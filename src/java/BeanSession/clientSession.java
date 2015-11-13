/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
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
    private static List<FacesContext> clients;

    public void initClients(){
        if(clients == null)
            clients = new ArrayList<FacesContext>();
        clients.add(FacesContext.getCurrentInstance());
    }
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

    
    @Override
    public void refresh(){
        for(int i = 0 ; i < clients.size() ; i++){
    FacesContext context = clients.get(i);
    String viewId = context.getViewRoot().getViewId();
    ViewHandler handler = context.getApplication().getViewHandler();
    UIViewRoot root = handler.createView(context, viewId);
    root.setViewId(viewId);
    context.setViewRoot(root);
        }
}
}
