/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import BeanEntity.Route;
import com.pauware.pauware_engine._Exception.Statechart_exception;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
    static private String[] roadForPolice ={};
    static private String[] roadForFire ={};
    static private boolean loaderRunning = true;
    static private String stateSession = "Session not create";
    
    public FSC getFire(){ return fire;}
    public PSC getPolice(){ return police;}
    public String getStateSession(){return stateSession;}
    public clientSessionLocal getClient(){ return client;}
    public BCMSDaoLocalReader getDao(){return dao;}
    public int getNbPoliceVehicule(){return nbPoliceVehicule;}
    public int getNbFireVehicule(){return nbFireVehicule;}
    public String[] getRoadForPolice(){
        return roadForPolice;
    }
    public String[] getRoadForFire(){
        return roadForFire;
    }
    
    public void setStateSession(String s){stateSession = s;}
    public void setNbPoliceVehicule(int i){nbPoliceVehicule = i;}
    public void setNbFireVehicule(int i){nbFireVehicule = i;}
    public void setRoadForPolice(String[] r){
        roadForPolice = r;
    }
    public void setRoadForFire(String[] r){
        roadForFire = r;
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
            setStateSession("Session created");
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
            setStateSession("Session created");
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
            ch = "folowFireman";
           
        return ch;
        
    }
    public String actionPolicemanConnect(){
        String ch ="";
        if(!client.getPoliceSessionState())
            ch = connectPoliceman();
        else if(client.getPoliceSessionState() && !client.isPoliceSession())
            ch = "folowFireman";
           
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

        return "policeSession";
    }
    public String waitRoute(){
        System.out.println("------------------");
        System.out.println("wait Road : "+ (roadForPolice.length == 0));
        System.out.println("__________________");
        return (roadForPolice.length == 0 || roadForFire.length == 0)? "" : "stopPlugin()";
    }
    
    public boolean roadNotReceived(){
        return (roadForPolice.length == 0 || roadForFire.length == 0);
    }
    public void setLoaderRunning(boolean b){
        loaderRunning = b;
    }
    public boolean loaderIsRunning(){
        return loaderRunning;
    }
}