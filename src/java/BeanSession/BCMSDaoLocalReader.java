/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import BeanEntity.BcmsSession;
import BeanEntity.Event;
import BeanEntity.FireTruck;
import BeanEntity.PoliceVehicle;
import BeanEntity.Route;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author berger
 */
@Local
public interface BCMSDaoLocalReader {

    public Long countFireTruck();
    public Long countPoliceVehicle();
    
    public List<FireTruck> getFireTruck();
    public List<Event> getEvent();
    public List<Event> getEventBySession();
    public Route getRoute(String name);
    public FireTruck getFireTruck(String name);
    public PoliceVehicle getPoliceVehicule(String name);
    public List<BcmsSession> getAllSession();
    public List<String> getNameRoutes();
    
    public void setNbFireTruck(int nb);
    public void setNbPoliceVehicule(int nb);
    
    public boolean sessionIsCreated();
    
    public boolean sessionHaveEvent();

}
