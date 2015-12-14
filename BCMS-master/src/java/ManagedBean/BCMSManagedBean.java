/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import BeanSession.FSC;
import BeanSession.PSC;
import DAO.BCMSDaoLocalReader;
import com.pauware.pauware_engine._Exception.Statechart_exception;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author berger
 */
@ApplicationScoped
@ManagedBean(name = "bcms")
public class BCMSManagedBean {

    @EJB
    private FSC fire;
    @EJB
    private PSC police;
    @EJB
    private clientSessionLocal client;
    @EJB
    private BCMSDaoLocalReader dao;

    private int nbPoliceVehicule = 0;
    private int nbFireVehicule = 0;
    private String[] fireTruck;
    private String[] policeVehicle;
    private String roadForPolice = "";
    private String roadForFire = "";
    private boolean fireAgreeWithFireRoads = false;
    private boolean fireAgreeWithPoliceRoads = false;
    private boolean fireDisagreeWithFireRoads = true;
    private boolean fireDisagreeWithPoliceRoads = true;
    private boolean fireSendRoads = false;
    private boolean policeSendRoads = false;
    private boolean sessionCloseByPolice = false;
    private boolean sessionCloseByFire = false;
    private int CURRENT_STATE_FIRE = 0;
    private int CURRENT_STATE_POLICE = 0;

    private void init() {
        nbPoliceVehicule = 0;
        nbFireVehicule = 0;
        fireTruck = null;
        policeVehicle = null;
        roadForPolice = "";
        roadForFire = "";
        fireAgreeWithFireRoads = false;
        fireAgreeWithPoliceRoads = false;
        fireDisagreeWithFireRoads = true;
        fireDisagreeWithPoliceRoads = true;
        fireSendRoads = false;
        policeSendRoads = false;
        sessionCloseByPolice = false;
        sessionCloseByFire = false;
        CURRENT_STATE_FIRE = 0;
        CURRENT_STATE_POLICE = 0;
        client.init();
    }

    public int getCURRENT_STATE_FIRE() {
        return CURRENT_STATE_FIRE;
    }

    public int getCURRENT_STATE_POLICE() {
        return CURRENT_STATE_POLICE;
    }

    public clientSessionLocal getClient() {
        return client;
    }

    public BCMSDaoLocalReader getDao() {
        return dao;
    }

    public int getNbPoliceVehicule() {
        return nbPoliceVehicule;
    }

    public int getNbFireVehicule() {
        return nbFireVehicule;
    }

    public String getRoadForPolice() {
        return roadForPolice;
    }

    public String getRoadForFire() {
        return roadForFire;
    }

    public boolean getFireAgreeWithFireRoads() {
        return fireAgreeWithFireRoads;
    }

    public boolean getFireAgreeWithPoliceRoads() {
        return fireAgreeWithPoliceRoads;
    }

    public boolean getFireDisagreeWithFireRoads() {
        return fireDisagreeWithFireRoads;
    }

    public boolean getFireDisagreeWithPoliceRoads() {
        return fireDisagreeWithPoliceRoads;
    }

    public boolean getFireSendRoads() {
        return fireSendRoads;
    }

    public void setNbPoliceVehicule(int i) {
        nbPoliceVehicule = i;
    }

    public void setNbFireVehicule(int i) {
        nbFireVehicule = i;
    }

    public void setRoadForPolice(String r) {
        roadForPolice = r;
    }

    public void setRoadForFire(String r) {
        roadForFire = r;
    }

    public String stateFireWithFireRoads() {
        String result;
        if (fireAgreeWithFireRoads && !fireDisagreeWithFireRoads) {
            result = "Fireman agree with police road(s)";
        } else if (fireAgreeWithFireRoads && fireDisagreeWithFireRoads) {
            result = "Fireman disagree with police road(s)";
        } else {
            result = "Waiting fireman";
        }
        return result;
    }

    public String stateFireWithPoliceRoads() {
        String result;
        if (fireAgreeWithPoliceRoads && !fireDisagreeWithPoliceRoads) {
            result = "Policeman agree with police road(s)";
        } else if (fireAgreeWithPoliceRoads && fireDisagreeWithPoliceRoads) {
            result = "Policeman disagree with police road(s)";
        } else {
            result = "Waiting Policeman";
        }
        return result;
    }

    public String connectFireman() {
        try {
            if (client.isFireSession() || !client.getFireSessionState()) {

                if (!client.getFireSession()) {
                    fire.FSC_connection_request();
                }
                if (!client.getFireSessionState()) {
                    client.setFireSessionHttp();
                }
            }
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        CURRENT_STATE_FIRE = 1;
        return "fireConnected?faces-redirect=true";
    }

    public String connectPoliceman() {
        try {
            if (client.isPoliceSession() || !client.getPoliceSessionState()) {

                if (!client.getPoliceSession()) {
                    police.PSC_connection_request();
                }
                if (!client.getPoliceSessionState()) {
                    client.setPoliceSessionHttp();
                }
            }
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        CURRENT_STATE_POLICE = 1;
        return "policeConnected?faces-redirect=true";
    }

    public String deconnectPoliceman() {
        client.deletePoliceSessionHttp();
        CURRENT_STATE_POLICE = 0;
        return "policeConnected?faces-redirect=true";
    }

    public String deconnectFireman() {
        client.deleteFireSessionHttp();
        CURRENT_STATE_FIRE = 0;
        return "fireConnected?faces-redirect=true";
    }

    public String actionFiremanConnect() {
        String ch = "";
        if (!client.getFireSessionState()) {
            ch = connectFireman();
        } else if (client.getFireSessionState() && !client.isFireSession()) {
            ch = "folowFireman?faces-redirect=true";
        }

        return ch;

    }

    public String actionPolicemanConnect() {
        String ch = "";
        if (!client.getPoliceSessionState()) {
            ch = connectPoliceman();
        } else if (client.getPoliceSessionState() && !client.isPoliceSession()) {
            ch = "folowPoliceman?faces-redirect=true";
        }

        return ch;

    }

    public String runOrFollowFireSession() {
        String ch = "";
        if (!client.getFireSessionState()) {
            ch = "Fire connexion";
        } else if (client.getFireSessionState() && !client.isFireSession()) {
            ch = "Follow the session";
        }

        return ch;
    }

    public String runOrFollowPoliceSession() {
        String ch = "";
        if (!client.getPoliceSessionState()) {
            ch = "Police connexion";
        } else if (client.getPoliceSessionState() && !client.isPoliceSession()) {
            ch = "Follow the session";
        }

        return ch;
    }

    public boolean roadNotReceived() {
        return (policeRoadNotReceived() || fireRoadNotReceived());
    }

    public boolean isRoadAgree() {
        return ((fireAgreeWithFireRoads && fireAgreeWithPoliceRoads) && (!fireDisagreeWithFireRoads && !fireDisagreeWithPoliceRoads));
    }

    public boolean policeRoadNotReceived() {
        return (roadForPolice.equals(""));
    }

    public boolean fireRoadNotReceived() {
        return (roadForFire.equals(""));
    }

    public String onClickAgreeWithFireRoads() {
        try {
            fireAgreeWithFireRoads = true;
            fireDisagreeWithFireRoads = false;
            fire.FSC_agrees_about_fire_truck_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }

    public String onClickAgreeWithPoliceRoads() {
        try {
            fireAgreeWithPoliceRoads = true;
            fireDisagreeWithPoliceRoads = false;
            fire.FSC_agrees_about_police_vehicle_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }

    public String onClickDisagreeWithFireRoads() {
        try {
            fireSendRoads = false;
            fireAgreeWithFireRoads = true;
            fireDisagreeWithFireRoads = true;
            fire.FSC_disagrees_about_fire_truck_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }

    public String onClickDisagreeWithPoliceRoads() {
        try {
            policeSendRoads = false;
            fireAgreeWithPoliceRoads = true;
            fireDisagreeWithPoliceRoads = true;
            fire.FSC_disagrees_about_police_vehicle_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }

    public String styleBoutonDisagreeWithPoliceRoads() {
        String result = "btn btn-default";
        if (fireAgreeWithPoliceRoads && fireDisagreeWithPoliceRoads) {
            result = "btn btn-danger";
        }
        return result;
    }

    public String styleBoutonDisagreeWithFireRoads() {
        String result = "btn btn-default";
        if (fireAgreeWithFireRoads && fireDisagreeWithFireRoads) {
            result = "btn btn-danger";
        }
        return result;
    }

    public String styleBoutonAgreeWithPoliceRoads() {
        String result = "btn btn-default";
        if (fireAgreeWithPoliceRoads && !fireDisagreeWithPoliceRoads) {
            result = "btn btn-success";
        }
        return result;
    }

    public String styleBoutonAgreeWithFireRoads() {
        String result = "btn btn-default";
        if (fireAgreeWithFireRoads && !fireDisagreeWithFireRoads) {
            result = "btn btn-success";
        }
        return result;
    }

    public String onClickFireSendRoads() throws Statechart_exception {
        if (!fireAgreeWithFireRoads || (fireAgreeWithFireRoads && fireDisagreeWithFireRoads)) {
            if (!roadForFire.equals("")) {
                fireSendRoads = true;
                fire.route_for_fire_trucks(roadForFire);
            }
        }
        return "policeConnected?faces-redirect=true";
    }

    public String onClickPoliceSendRoads() throws Statechart_exception {
        if (!fireAgreeWithPoliceRoads || (fireAgreeWithPoliceRoads && fireDisagreeWithPoliceRoads)) {
            if (!roadForPolice.equals("")) {
                policeSendRoads = true;
                police.route_for_police_vehicles(roadForPolice);
            }
        }
        return "policeConnected?faces-redirect=true";
    }

    public String goToState2ByPolice() {
        try {
            policeVehicle = new String[nbPoliceVehicule];
            for (int i = 0; i < nbPoliceVehicule; i++) {
                policeVehicle[i] = dao.getPoliceVehicle().get(i).getPoliceVehicleName();
            }
            police.state_police_vehicle_number(nbPoliceVehicule);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        CURRENT_STATE_POLICE = 2;
        return "policeConnected?faces-redirect=true";
    }

    public String goToState3ByPolice() {
        CURRENT_STATE_POLICE = 3;
        return "policeConnected?faces-redirect=true";
    }

    public String goToState2ByFire() {
        try {
            fireTruck = new String[nbFireVehicule];
            for (int i = 0; i < nbFireVehicule; i++) {
                fireTruck[i] = dao.getFireTruck().get(i).getFireTruckName();
            }
            fire.state_fire_truck_number(nbFireVehicule);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        CURRENT_STATE_FIRE = 2;
        return "fireConnected?faces-redirect=true";
    }

    public String goToState3ByFire() {
        CURRENT_STATE_FIRE = 3;
        return "fireConnected?faces-redirect=true";
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy managed bean");
        //client.destroy();
        client = null;
        fire = null;
        police = null;
    }

    public String[] getFireTruck() {
        return fireTruck;
    }

    public void setFireTruck(String[] fireTruck) {
        this.fireTruck = fireTruck;
    }

    public String[] getPoliceVehicle() {
        return policeVehicle;
    }

    public void setPoliceVehicle(String[] policeVehicle) {
        this.policeVehicle = policeVehicle;
    }

    public void fireTruckDispatched(String truck) {
        try {
            fire.fire_truck_dispatched(truck);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fireTruckArrived(String truck) {
        try {
            fire.fire_truck_arrived(truck);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fireTruckBreakDown(String truck, String rplc) {
        try {
            fire.fire_truck_breakdown(truck, rplc);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fireTruckBlocked(String truck) {
        try {
            fire.fire_truck_blocked(truck);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void policeVehicleDispatched(String vehicle) {
        try {
            police.police_vehicle_dispatched(vehicle);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void policeVehicleArrived(String vehicle) {
        try {
            police.police_vehicle_arrived(vehicle);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void policeVehicleBreakDown(String vehicle, String rplc) {
        try {
            police.police_vehicle_breakdown(vehicle, rplc);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void policeVehicleBlocked(String vehicle) {
        try {
            police.police_vehicle_blocked(vehicle);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeSessionByPolice() throws Statechart_exception {
        sessionCloseByPolice = true;
        CURRENT_STATE_POLICE = -1;
        if (sessionCloseByFire && sessionCloseByPolice) {
            police.close();
            police.reset();
            init();
        }
    }

    public void closeSessionByFire() throws Statechart_exception {
        sessionCloseByFire = true;
        CURRENT_STATE_FIRE = -1;
        if (sessionCloseByFire && sessionCloseByPolice) {
            fire.close();
            fire.reset();
            init();
        }
    }

    /**
     * @return the policeSendRoads
     */
    public boolean isPoliceSendRoads() {
        return policeSendRoads;
    }

    /**
     * @return the sessionCloseByPolice
     */
    public boolean isSessionCloseByPolice() {
        return sessionCloseByPolice;
    }

    /**
     * @param sessionCloseByPolice the sessionCloseByPolice to set
     */
    public void setSessionCloseByPolice(boolean sessionCloseByPolice) {
        this.sessionCloseByPolice = sessionCloseByPolice;
    }

    /**
     * @return the sessionCloseByFire
     */
    public boolean isSessionCloseByFire() {
        return sessionCloseByFire;
    }

    /**
     * @param sessionCloseByFire the sessionCloseByFire to set
     */
    public void setSessionCloseByFire(boolean sessionCloseByFire) {
        this.sessionCloseByFire = sessionCloseByFire;
    }

    public boolean isDisabledAction(String state, String bouton) {
        boolean result = true;
        switch (state) {
            case "Idle":
                switch (bouton) {
                    case "Dispatched":
                        result = false;
                        break;
                }
                break;
            case "Dispatched":
                switch (bouton) {
                    case "BreakDown":
                        result = false;
                        break;
                    case "Blocked":
                        result = false;
                        break;
                    case "Arrived":
                        result = false;
                    case "Dispatched":
                        result = false;
                        break;
                }
                break;
            case "Breakdown":
                switch (bouton) {
                    case "Dispatched":
                        result = false;
                        break;
                    case "BreakDown":
                        result = false;
                }
                break;
            case "Arrived":
                switch (bouton) {
                    case "Arrived":
                        result = false;
                        break;
                }
                break; 
            case "Blocked":
                switch (bouton) {
                    case "Blocked":
                        result = false;
                        break;
                }
                break;  
        }

        return result;
    }
private boolean followRoadFire;
private boolean followRoadPolice;
    public boolean isFollowRoadFire() {
        return followRoadFire;
    }

    public void setFollowRoadFire(boolean followRoadFire) {
        this.followRoadFire = followRoadFire;
    }

    /**
     * @return the followRoadPolice
     */
    public boolean isFollowRoadPolice() {
        return followRoadPolice;
    }

    /**
     * @param followRoadPolice the followRoadPolice to set
     */
    public void setFollowRoadPolice(boolean followRoadPolice) {
        this.followRoadPolice = followRoadPolice;
    }
}
