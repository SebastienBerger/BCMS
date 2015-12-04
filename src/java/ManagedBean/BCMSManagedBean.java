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
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author berger
 */
@ApplicationScoped
@ManagedBean(name = "bcms")
public class BCMSManagedBean{

    @EJB
    private FSC fire;
    @EJB
    private PSC police;
    @EJB 
    private clientSessionLocal client;
    @EJB
    private BCMSDaoLocalReader dao;
  
    private int nbPoliceVehicule;
    private int nbFireVehicule;
    private String roadForPolice ="";
    private String roadForFire ="";
    private boolean fireAgreeWithFireRoads = false;
    private boolean fireAgreeWithPoliceRoads = false;
    private boolean fireDisagreeWithFireRoads = true;
    private boolean fireDisagreeWithPoliceRoads = true;
    private boolean fireSendRoads = false;
    private boolean isOnPoliceSession= false;
    private boolean isOnFireSession= false;

    @PostConstruct
    public void init(){
        System.out.println("init");
    }
    
    public clientSessionLocal getClient(){ return client;}
    public BCMSDaoLocalReader getDao(){return dao;}
    public int getNbPoliceVehicule(){return nbPoliceVehicule;}
    public int getNbFireVehicule(){return nbFireVehicule;}
    public String getRoadForPolice(){ return roadForPolice;}
    public String getRoadForFire(){ return roadForFire;}
    public boolean getFireAgreeWithFireRoads(){return fireAgreeWithFireRoads;}
    public boolean getFireAgreeWithPoliceRoads(){return fireAgreeWithPoliceRoads;}
    public boolean getFireDisagreeWithFireRoads(){return fireDisagreeWithFireRoads;}
    public boolean getFireDisagreeWithPoliceRoads(){return fireDisagreeWithPoliceRoads;}
    public boolean getFireSendRoads(){return fireSendRoads;}
    public boolean getIsOnPoliceSession(){return isOnPoliceSession;}
    public boolean getIsOnFireSession(){return isOnFireSession;}

    public void setNbPoliceVehicule(int i){nbPoliceVehicule = i;}
    public void setNbFireVehicule(int i){nbFireVehicule = i;}
    public void setRoadForPolice(String r){roadForPolice = r;}
    public void setRoadForFire(String r){roadForFire = r;}
    public void setFireSendRoads(boolean f){
        fireSendRoads = f;
    }
    
    public String stateFireWithFireRoads(){
        String result;
        if(fireAgreeWithFireRoads && !fireDisagreeWithFireRoads)
            result = "Fireman agree with police road(s)";
        else if(fireAgreeWithFireRoads && fireDisagreeWithFireRoads)
            result = "Fireman disagree with police road(s)";
        else
            result = "Waiting fireman";
        return result;
    }
    public String stateFireWithPoliceRoads(){
        String result;
        if(fireAgreeWithPoliceRoads && !fireDisagreeWithPoliceRoads)
            result = "Policeman agree with police road(s)";
        else if(fireAgreeWithPoliceRoads && fireDisagreeWithPoliceRoads)
            result = "Policeman disagree with police road(s)";
        else
            result = "Waiting Policeman";
        return result;
    }
        
 
    public String connectFireman(){
        try {
            if(client.isFireSession() || !client.getFireSessionState()){

                    if(!client.getFireSession()){
                        fire.FSC_connection_request();
                    }
                    if(!client.getFireSessionState())
                        client.setFireSessionHttp();
            }
        } catch (Statechart_exception ex) {
                Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }
    
    public String connectPoliceman(){
        try {
            if(client.isPoliceSession() || !client.getPoliceSessionState()){

                    if(!client.getPoliceSession()){
                        police.PSC_connection_request();
                    }
                    if(!client.getPoliceSessionState())
                        client.setPoliceSessionHttp();
            }
        } catch (Statechart_exception ex) {
                Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "policeConnected?faces-redirect=true";
    }
    
    public String deconnectPoliceman(){
        client.deletePoliceSessionHttp();
        return "policeConnected?faces-redirect=true";
    }

    public String deconnectFireman(){
        client.deleteFireSessionHttp();
        return "fireConnected?faces-redirect=true";
    }
    
    public String actionFiremanConnect(){
        String ch ="";
        if(!client.getFireSessionState())
            ch = connectFireman();
        else if(client.getFireSessionState() && !client.isFireSession())
            ch = "folowFireman?faces-redirect=true";
           
        return ch;
        
    }
    public String actionPolicemanConnect(){
        String ch ="";                                                                                                                                                                                                                                                                                                                             if(!client.getPoliceSessionState())
            ch = connectPoliceman();
        else if(client.getPoliceSessionState() && !client.isPoliceSession())
            ch = "folowPoliceman?faces-redirect=true";
           
        return ch;
        
    }
    public String runOrFollowFireSession(){
        String ch ="";
        if(!client.getFireSessionState())
            ch = "Fire connexion" ;
        else if(client.getFireSessionState() && !client.isFireSession())
            ch = "Follow the session";
           
        return ch;
    }
    public String runOrFollowPoliceSession(){
        String ch ="";
        if(!client.getPoliceSessionState())
            ch = "Police connexion" ;
        else if(client.getPoliceSessionState() && !client.isPoliceSession())
            ch = "Follow the session";
           
        return ch;  
    }
    public String goToPoliceSession(){
        try {
            police.state_police_vehicle_number(nbPoliceVehicule);
            isOnPoliceSession = true;
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "policeSession?faces-redirect=true";
    }
    public String goToFireSession(){
        try {
            fire.state_fire_truck_number(nbFireVehicule);
            isOnFireSession = true;
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireSession?faces-redirect=true";
    }
    
    public boolean roadNotReceived(){
        return (policeRoadNotReceived() || fireRoadNotReceived());
    } 
    public boolean isRoadAgree(){
        return ((fireAgreeWithFireRoads && fireAgreeWithPoliceRoads) && (!fireDisagreeWithFireRoads && !fireDisagreeWithPoliceRoads));
    } 
    public boolean policeRoadNotReceived(){
        return (roadForPolice.equals(""));
    }
    public boolean fireRoadNotReceived(){
        return (roadForFire.equals(""));
    }
    
    public String onClickAgreeWithFireRoads(){
        try {
            fireAgreeWithFireRoads = true;
            fireDisagreeWithFireRoads = false;
            fire.FSC_agrees_about_fire_truck_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }
    public String onClickAgreeWithPoliceRoads(){
        try {
            fireAgreeWithPoliceRoads = true;
            fireDisagreeWithPoliceRoads = false;
            fire.FSC_agrees_about_police_vehicle_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }
    public String onClickDisagreeWithFireRoads(){
        try {
            fireAgreeWithFireRoads = true;
            fireDisagreeWithFireRoads = true;
            fire.FSC_disagrees_about_fire_truck_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }
    public String onClickDisagreeWithPoliceRoads(){
        try {
            fireAgreeWithPoliceRoads = true;
            fireDisagreeWithPoliceRoads = true;
            fire.FSC_disagrees_about_police_vehicle_route();
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fireConnected?faces-redirect=true";
    }
    public String styleBoutonDisagreeWithPoliceRoads(){
        String result = "btn btn-default";
        if(fireAgreeWithPoliceRoads && fireDisagreeWithPoliceRoads)
            result = "btn btn-danger";
        return result;
    }
    public String styleBoutonDisagreeWithFireRoads(){
        String result = "btn btn-default";
        if(fireAgreeWithFireRoads && fireDisagreeWithFireRoads)
            result = "btn btn-danger";
        return result;
    }
    public String styleBoutonAgreeWithPoliceRoads(){
        String result = "btn btn-default";
        if(fireAgreeWithPoliceRoads && !fireDisagreeWithPoliceRoads)
            result = "btn btn-success";
        return result;
    }
    public String styleBoutonAgreeWithFireRoads(){
        String result = "btn btn-default";
        if(fireAgreeWithFireRoads && !fireDisagreeWithFireRoads)
            result = "btn btn-success";
        return result;
    }
    public String onClickFireSendRoads(){
        try {
            setFireSendRoads(true);
            if(!fireAgreeWithFireRoads || (fireAgreeWithFireRoads && fireDisagreeWithFireRoads))
                fire.route_for_fire_trucks(roadForFire);
            if(!fireAgreeWithPoliceRoads || (fireAgreeWithPoliceRoads && fireDisagreeWithPoliceRoads))
                police.route_for_police_vehicles(roadForPolice);
        } catch (Statechart_exception ex) {
            Logger.getLogger(BCMSManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "policeConnected?faces-redirect=true";
    }
    
    @PreDestroy
    public void destroy(){
        System.out.println("destroy managed bean");
        client.destroy();
        client = null;
        fire = null;
        police = null;
    }
}