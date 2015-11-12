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
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
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

    static private String stateSession = "Session not create";
    
    public FSC getFire(){ return fire;}
    public PSC getPolice(){ return police;}
    public String getStateSession(){return stateSession;}
    public void setStateSession(String s){stateSession = s;}
    public clientSessionLocal getClient(){ return client;}
    public BCMSDaoLocalReader getDao(){return dao;}
    
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
}
