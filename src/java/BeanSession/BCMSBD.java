/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanSession;

import BeanEntity.BcmsSession;
import BeanEntity.BcmsSessionFireTruck;
import BeanEntity.Event;
import BeanEntity.FireTruck;
import BeanEntity.PoliceVehicle;
import BeanEntity.Route;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author berger
 */
@Singleton
public class BCMSBD implements BCMSBDLocal {

    @PersistenceContext(name="BCMSPU")
    private EntityManager _entity_manager;
    
    private BcmsSession _session;
    
    private String date(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date today = Calendar.getInstance().getTime();        
            String reportDate = df.format(today);
            return reportDate;
    }
    
    @Override
    public void createEvent(String request, String exec) {
        Event event = new Event();
        event.setSessionId(_session);
        String name = request + date();
        event.setEventName(name);
        event.setExecutionTrace(exec);
        _entity_manager.persist(event);
    }

    @Override
    public void createSession() {
            _session = new BcmsSession();
            
            String namesession = this.getClass().getSimpleName() + date();
            _session.setSessionId(namesession);
            _entity_manager.persist(_session);
    }

    @Override
    public void createSessionFireTruck(FireTruck ft, int id) {
            BcmsSessionFireTruck bcmsft = new BcmsSessionFireTruck();
            bcmsft.setSessionId(_session);
            bcmsft.setFireTruckName(ft);
            bcmsft.setFireTruckStatus("Idle");
            bcmsft.setBsftId(date()+id);
            _entity_manager.persist(bcmsft);
    }

    @Override
    public Long countFireTruck() {
        return (Long)_entity_manager.createNamedQuery("FireTruck.countFireTruck").getSingleResult();
    }

    @Override
    public Long countPoliceVehicle() {
        return (Long)_entity_manager.createNamedQuery("PoliceVehicle.countPoliceVehicle").getSingleResult();
    }

    @Override
    public List<FireTruck> getFireTruck() {
        return _entity_manager.createNamedQuery("FireTruck.findAll").getResultList();
    }
    
    @Override
    public Route getRoute(String name) {
        return _entity_manager.find(Route.class, name);
    }
    
    @Override
    public FireTruck getFireTruck(String name) {
        return _entity_manager.find(FireTruck.class, name);
    }

    @Override
    public PoliceVehicle getPoliceVehicule(String name) {
        return _entity_manager.find(PoliceVehicle.class, name);
    }

    @Override
    public void setNbFireTruck(int nb) {
        _session.setNbTruckF(nb);
        _entity_manager.persist(_session);
    }

    @Override
    public void setNbPoliceVehicule(int nb) {
        _session.setNbTruckP(nb);
        _entity_manager.persist(_session); 
    }






}