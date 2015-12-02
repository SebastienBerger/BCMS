package BeanEntity;

import BeanEntity.BcmsSession;
import BeanEntity.FireTruck;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-01T22:36:22")
@StaticMetamodel(BcmsSessionFireTruck.class)
public class BcmsSessionFireTruck_ { 

    public static volatile SingularAttribute<BcmsSessionFireTruck, String> fireTruckStatus;
    public static volatile SingularAttribute<BcmsSessionFireTruck, String> bsftId;
    public static volatile SingularAttribute<BcmsSessionFireTruck, FireTruck> fireTruckName;
    public static volatile SingularAttribute<BcmsSessionFireTruck, BcmsSession> sessionId;

}