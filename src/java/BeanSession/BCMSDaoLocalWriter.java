/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import BeanEntity.FireTruck;
import javax.ejb.Local;

/**
 *
 * @author berger
 */
@Local
public interface BCMSDaoLocalWriter {
    public void createEvent(String request, String exec, String caller);
    public void createSession();
    public void createSessionFireTruck(FireTruck ft, int id);
       
}
