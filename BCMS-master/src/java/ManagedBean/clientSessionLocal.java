/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBean;

import javax.ejb.Local;
import javax.servlet.http.HttpSession;

/**
 *
 * @author berger
 */
@Local
public interface clientSessionLocal {

    public boolean getFireSession();

    public boolean getPoliceSession();

    public HttpSession getPoliceSessionHttp();

    public HttpSession getFireSessionHttp();

    public void setFireSession(boolean b);

    public void setPoliceSession(boolean b);

    public void setFireSessionHttp();

    public void setPoliceSessionHttp();

    public boolean isPoliceSession();

    public boolean isFireSession();

    public void deleteFireSessionHttp();

    public boolean getFireSessionState();

    public boolean getPoliceSessionState();

    public void setPoliceSessionState(boolean b);

    public void setFireSessionState(boolean b);

    public void deletePoliceSessionHttp();

    public void refresh();

    public void initClients();
    
    public void init();
    
    public void destroy();
}
