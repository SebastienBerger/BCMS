/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author berger
 */
@Singleton
public class clientSession implements clientSessionLocal {

    private static boolean fireSession = false;
    private static boolean fireSessionState = false;
    private static boolean policeSession = false;
    private static boolean policeSessionState = false;
    private static HttpSession _fireSession;
    private static HttpSession _policeSession;  
    

    @Override
    public boolean getFireSession(){
        return fireSession;
    }
    @Override
    public boolean getFireSessionState(){
        return fireSessionState;
    } 
    @Override
    public boolean getPoliceSession(){
        return policeSession;
    }
    @Override
    public boolean getPoliceSessionState(){
        return policeSessionState;
    } 
    @Override
    public void setFireSession(boolean b){
        fireSession = b;
    }
    @Override
    public void setPoliceSession(boolean b){
        policeSession = b;
    }
    @Override
    public void setFireSessionState(boolean b){
        fireSessionState = b;
    }
    @Override
    public void setPoliceSessionState(boolean b){
        policeSessionState = b;
    } 
    
    @Override
    public boolean isPoliceSession(){
        System.out.println("isPoliceSession()");
        if(_policeSession == null)
            return false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        System.out.println(_policeSession.getId()+" = "+session.getId());
        System.out.println("policeSession = "+policeSession);
        return _policeSession.getId().equals(session.getId());
    }
    @Override
    public boolean isFireSession(){
        System.out.println("isFireSession()");
        if(_fireSession == null)
            return false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        System.out.println(_fireSession.getId()+" = "+session.getId());
        return _fireSession.getId().equals(session.getId());
    }
    @Override
    public HttpSession getPoliceSessionHttp(){      
        return _policeSession;
    }
    @Override
    public HttpSession getFireSessionHttp(){      
        return _fireSession;
    }
    @Override
    public void setFireSessionHttp(){      
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        _fireSession = session;
        setFireSessionState(true);
        setFireSession(true);
        System.out.println("setFireSessionHttp fireSession = "+fireSession);
        System.out.println(_fireSession.getId());
    }
    @Override
    public void setPoliceSessionHttp(){      
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        _policeSession = session;
        setPoliceSessionState(true);
        setPoliceSession(true);
        System.out.println("setFireSessionHttp policeSession = "+policeSession);
        System.out.println(_policeSession.getId());
    }
    
    @Override
    public void deleteFireSessionHttp(){   
        System.out.println("deleteFireSessionHttp");
        fireSessionState = false;
        _fireSession = null;
    }
    
        @Override
    public void deletePoliceSessionHttp(){   
        System.out.println("deleteFireSessionHttp");
        policeSessionState = false;
        _policeSession = null;
    }

}
