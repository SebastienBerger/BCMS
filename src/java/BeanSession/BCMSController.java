/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import BeanEntity.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author berger
 */
@ManagedBean(name = "bcms")
@RequestScoped
public class BCMSController {

    /**
     * Creates a new instance of BCMSController
     */
   //@EJB(lookup = "BCMSSingleton" , mappedName = "BCMSSingleton")
    //BCMS service = null;
    //private static BCMS service;

    private List<Event> event;
    public List<Event> getEvent(){
        //event = (List<Event>) getEvents();
        System.out.println(event);
        return event;
    }
    public void setEvent(List<Event> s){
        event=s;
    }
    
    private boolean _PSC_is_connected = false;
    private boolean _FSC_is_connected = false;

    public boolean isCreateSession(){
        if(_PSC_is_connected){
            return true;
        }
        else
            return false;
    }
    public void createSession(){
        //if(service._session == null){
            //start();
        //}
    }
    public boolean a_PSC_session_is_active(){
        return _PSC_is_connected ? true : false ;
    }
    public boolean a_FSC_session_is_active(){
        return _FSC_is_connected ? true : false ;
    }
    
}
