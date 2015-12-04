/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BeanEntity.BcmsSession;
import BeanEntity.BcmsSessionFireTruck;
import BeanEntity.BcmsSessionPoliceVehicle;
import BeanEntity.Event;
import BeanEntity.FireTruck;
import BeanEntity.PoliceVehicle;
import BeanEntity.Route;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author berger
 */
@Stateless
public class BCMSDao implements BCMSDaoLocalReader, BCMSDaoLocalWriter {

    @PersistenceContext(name = "BCMSPU")
    private EntityManager _entity_manager;

    private static BcmsSession _session = null;

    private String date() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
    
    @Override
    public void stopSession(){
        _session = null;
    }

    @Override
    public void createEvent(String request, String exec, String caller) {
        Event event = new Event();
        event.setSessionId(_session);
        String name = request + date();
        event.setEventName(name);
        event.setExecutionTrace(exec);
        event.setCaller(caller);
        _entity_manager.persist(event);
    }
    
    @Override
    public void createEvents(String session, String request, String exec, String caller) {
        Event event = new Event();
        event.setSessionId(_session);
        String name = request + date() + session;
        event.setEventName(name);
        event.setExecutionTrace(exec);
        event.setCaller(caller);
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
        bcmsft.setBsftId(date() + id);
        _entity_manager.persist(bcmsft);
    }

    @Override
    public Long countFireTruck() {
        return (Long) _entity_manager.createNamedQuery("FireTruck.countFireTruck").getSingleResult();
    }

    @Override
    public Long countPoliceVehicle() {
        return (Long) _entity_manager.createNamedQuery("PoliceVehicle.countPoliceVehicle").getSingleResult();
    }

    @Override
    public List<FireTruck> getFireTruck() {
        return _entity_manager.createNamedQuery("FireTruck.findAll").getResultList();
    }

    @Override
    public List<Event> getEvent() {
        return _entity_manager.createNamedQuery("Event.findAll").getResultList();
    }

    @Override
    public List<Event> getEventBySession() {
        return _entity_manager.createNamedQuery("Event.findBySessionId").setParameter("sessionId", _session).getResultList();
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
    public List<BcmsSession> getAllSession() {
        return _entity_manager.createNamedQuery("BcmsSession.findAll").getResultList();
    }

    @Override
    public void setNbFireTruck(int nb) {
        _session.setNbTruckF(nb);
        _entity_manager.merge(_session);
    }

    @Override
    public void setNbPoliceVehicule(int nb) {
        _session.setNbTruckP(nb);
        _entity_manager.merge(_session);
    }

    @Override
    public boolean sessionIsCreated() {
        return _session != null;
    }

    @Override
    public boolean sessionHaveEvent() {
        return (Long) _entity_manager.createNamedQuery("Event.countEventBySessionId").setParameter("sessionId", _session).getSingleResult() > 0;
    }

    @Override
    public List<String> getNameRoutes() {
        return _entity_manager.createNamedQuery("Route.findAllName").getResultList();
    }

    @Override
    public List<BcmsSessionFireTruck> getBcmsSessionFireTruck() {
        return _entity_manager.createNamedQuery("BcmsSessionFireTruck.findAll").getResultList();
    }


    @Override
    public void setFireTruckStatus(BcmsSessionFireTruck bcmsft, String status) {
       bcmsft.setFireTruckStatus(status);
       _entity_manager.merge(bcmsft);
    }

    @Override
    public BcmsSessionFireTruck getBcmsSessionFireTruckByFireTruckName(String firetruckname) {
        return (BcmsSessionFireTruck)_entity_manager.createNamedQuery("BcmsSessionFireTruck.findByFireTruckNameAndSessionName").setParameter("fireTruckName", firetruckname).setParameter("sessionId", _session).getSingleResult();
    }

    @Override
    public void setPoliceVehicleStatus(BcmsSessionPoliceVehicle bcmspv, String status) {
       bcmspv.setPoliceVehicleStatus(status);
       _entity_manager.merge(bcmspv);
    }

    @Override
    public BcmsSessionPoliceVehicle getBcmsSessionPoliceVehicleByPoliceVehicleName(String policevehiclename) {
        return (BcmsSessionPoliceVehicle)_entity_manager.createNamedQuery("BcmsSessionPoliceVehicle.findByPoliceVehicleNameAndSessionName").setParameter("policeVehicleName", policevehiclename).setParameter("sessionId", _session).getSingleResult();
    }

    @Override
    public List<PoliceVehicle> getPoliceVehicle() {
        return _entity_manager.createNamedQuery("PoliceVehicle.findAll").getResultList();
    }

    @Override
    public void createSessionPoliceVehicle(PoliceVehicle ft, int id) {
        BcmsSessionPoliceVehicle bcmsft = new BcmsSessionPoliceVehicle();
        bcmsft.setSessionId(_session);
        bcmsft.setPoliceVehicleName(ft);
        bcmsft.setPoliceVehicleStatus("Idle");
        bcmsft.setBspvId(date() + id);
        _entity_manager.persist(bcmsft); 
    }

    @Override
    public List<Route> getRoutes(){
        return _entity_manager.createNamedQuery("Route.findAll").getResultList();
    }
}
