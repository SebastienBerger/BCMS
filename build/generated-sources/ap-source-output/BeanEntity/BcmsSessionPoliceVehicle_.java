package BeanEntity;

import BeanEntity.BcmsSession;
import BeanEntity.PoliceVehicle;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-01T22:36:22")
@StaticMetamodel(BcmsSessionPoliceVehicle.class)
public class BcmsSessionPoliceVehicle_ { 

    public static volatile SingularAttribute<BcmsSessionPoliceVehicle, PoliceVehicle> policeVehicleName;
    public static volatile SingularAttribute<BcmsSessionPoliceVehicle, String> policeVehicleStatus;
    public static volatile SingularAttribute<BcmsSessionPoliceVehicle, String> bspvId;
    public static volatile SingularAttribute<BcmsSessionPoliceVehicle, BcmsSession> sessionId;

}