/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BeanEntity.BcmsSessionFireTruck;
import BeanEntity.BcmsSessionPoliceVehicle;
import BeanEntity.FireTruck;
import BeanEntity.PoliceVehicle;
import javax.ejb.Local;

/**
 *
 * @author berger
 */
@Local
public interface BCMSDaoLocalWriter {
    public void createEvent(String request, String exec, String caller);
    public void createEvents(String session,String request, String exec, String caller);
    public void createSession();
    public void createSessionFireTruck(FireTruck ft, int id);
    public void createSessionPoliceVehicle(PoliceVehicle ft, int id);
    public void setNbFireTruck(int nb);
    public void setNbPoliceVehicule(int nb);
    public void setFireTruckStatus(BcmsSessionFireTruck bcmsft, String status);
    public void setPoliceVehicleStatus(BcmsSessionPoliceVehicle bcmspv, String status);
    public void stopSession();
}
