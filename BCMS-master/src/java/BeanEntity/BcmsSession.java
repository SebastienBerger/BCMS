/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanEntity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berger
 */
@Entity
@Table(name = "BCMS_SESSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BcmsSession.findAll", query = "SELECT b FROM BcmsSession b"),
    @NamedQuery(name = "BcmsSession.findByNbTruckF", query = "SELECT b FROM BcmsSession b WHERE b.nbTruckF = :nbTruckF"),
    @NamedQuery(name = "BcmsSession.findByNbTruckP", query = "SELECT b FROM BcmsSession b WHERE b.nbTruckP = :nbTruckP"),
    @NamedQuery(name = "BcmsSession.findBySessionId", query = "SELECT b FROM BcmsSession b WHERE b.sessionId = :sessionId")})
public class BcmsSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "NB_TRUCK_F")
    private Integer nbTruckF;
    @Column(name = "NB_TRUCK_P")
    private Integer nbTruckP;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SESSION_ID")
    private String sessionId;
    @OneToMany(mappedBy = "sessionId")
    private Collection<BcmsSessionFireTruck> bcmsSessionFireTruckCollection;
    @OneToMany(mappedBy = "sessionId")
    private Collection<BcmsSessionPoliceVehicle> bcmsSessionPoliceVehicleCollection;
    @OneToMany(mappedBy = "sessionId")
    private Collection<Event> eventCollection;

    public BcmsSession() {
    }

    public BcmsSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getNbTruckF() {
        return nbTruckF;
    }

    public void setNbTruckF(Integer nbTruckF) {
        this.nbTruckF = nbTruckF;
    }

    public Integer getNbTruckP() {
        return nbTruckP;
    }

    public void setNbTruckP(Integer nbTruckP) {
        this.nbTruckP = nbTruckP;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @XmlTransient
    public Collection<BcmsSessionFireTruck> getBcmsSessionFireTruckCollection() {
        return bcmsSessionFireTruckCollection;
    }

    public void setBcmsSessionFireTruckCollection(Collection<BcmsSessionFireTruck> bcmsSessionFireTruckCollection) {
        this.bcmsSessionFireTruckCollection = bcmsSessionFireTruckCollection;
    }

    @XmlTransient
    public Collection<BcmsSessionPoliceVehicle> getBcmsSessionPoliceVehicleCollection() {
        return bcmsSessionPoliceVehicleCollection;
    }

    public void setBcmsSessionPoliceVehicleCollection(Collection<BcmsSessionPoliceVehicle> bcmsSessionPoliceVehicleCollection) {
        this.bcmsSessionPoliceVehicleCollection = bcmsSessionPoliceVehicleCollection;
    }

    @XmlTransient
    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BcmsSession)) {
            return false;
        }
        BcmsSession other = (BcmsSession) object;
        if ((this.sessionId == null && other.sessionId != null) || (this.sessionId != null && !this.sessionId.equals(other.sessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BeanEntity.BcmsSession[ sessionId=" + sessionId + " ]";
    }
    
}
