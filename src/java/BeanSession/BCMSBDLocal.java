/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import BeanEntity.BcmsSession;
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
public interface BCMSBDLocal {
    public void createEvent(String request, String exec);
    public void createSession();
    public void createSessionFireTruck(FireTruck ft, int id);
    public Long countFireTruck();
    public Long countPoliceVehicle();
    public List<FireTruck> getFireTruck();
    public Route getRoute(String name);
    public FireTruck getFireTruck(String name);
    public PoliceVehicle getPoliceVehicule(String name);
    public void setNbFireTruck(int nb);
    public void setNbPoliceVehicule(int nb);
}