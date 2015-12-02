/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import com.pauware.pauware_engine._Exception.Statechart_exception;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;


/**
 *
 * @author berger
 */
@ManagedBean(name = "bcms")
@Singleton
public class BCMSManagedBean{

    @EJB
    private FSC fire;
    @EJB
    private PSC police;
    @EJB 
    private clientSessionLocal client;
    @EJB
    private BCMSDaoLocalReader dao;
  
    static private int nbPoliceVehicule;
    static private int nbFireVehicule;
    static private String roadForPolice ="";
    static private String roadForFire ="";
    static private boolean fireAgreeWithFireRoads = false;
    static private boolean fireAgreeWithPoliceRoads = false;
    static private boolean fireDisagreeWithFireRoads = true;
    static private boolean fireDisagreeWithPoliceRoads = true;
    static private boolean fireSendRoads = false;

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

    public void setNbPoliceVehicule(int i){nbPoliceVehicule = i;}
    public void setNbFireVehicule(int i){nbFireVehicule = i;}
    public void setRoadForPolice(String r){roadForPolice = r;}
    public void setRoadForFire(String r){roadForFire = r;}
    public void setFireSendRoads(boolean f){fireSendRoads = f;}
    
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
    
    public void deconnectPoliceman(){
        client.deletePoliceSessionHttp();
    }

    public void deconnectFireman(){
        client.deleteFireSessionHttp();
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
        return "policeSession?faces-redirect=true";
    }
    public String goToFireSession(){
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
    
    public void onClickAgreeWithFireRoads(){
        fireAgreeWithFireRoads = true;
        fireDisagreeWithFireRoads = false;
    }
    public void onClickAgreeWithPoliceRoads(){
        fireAgreeWithPoliceRoads = true;
        fireDisagreeWithPoliceRoads = false;
    }
    public void onClickDisagreeWithFireRoads(){
        fireAgreeWithFireRoads = true;
        fireDisagreeWithFireRoads = true;
    }
    public void onClickDisagreeWithPoliceRoads(){
        fireAgreeWithPoliceRoads = true;
        fireDisagreeWithPoliceRoads = true;
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
}