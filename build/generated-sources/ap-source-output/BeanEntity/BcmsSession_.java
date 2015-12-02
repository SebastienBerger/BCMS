package BeanEntity;

import BeanEntity.BcmsSessionFireTruck;
import BeanEntity.BcmsSessionPoliceVehicle;
import BeanEntity.Event;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-01T22:36:22")
@StaticMetamodel(BcmsSession.class)
public class BcmsSession_ { 

    public static volatile SingularAttribute<BcmsSession, Integer> nbTruckF;
    public static volatile CollectionAttribute<BcmsSession, Event> eventCollection;
    public static volatile CollectionAttribute<BcmsSession, BcmsSessionFireTruck> bcmsSessionFireTruckCollection;
    public static volatile SingularAttribute<BcmsSession, String> sessionId;
    public static volatile SingularAttribute<BcmsSession, Integer> nbTruckP;
    public static volatile CollectionAttribute<BcmsSession, BcmsSessionPoliceVehicle> bcmsSessionPoliceVehicleCollection;

}