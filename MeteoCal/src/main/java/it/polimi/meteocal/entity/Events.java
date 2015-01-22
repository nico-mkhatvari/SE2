/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.entity;

import CalendarNotifications.Notification;
import it.polimi.registration.business.security.entity.User;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jiasheng
 */
@Entity
@Table(name = "EVENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Events.findAll", query = "SELECT e FROM Events e"),
    @NamedQuery(name = "Events.findById", query = "SELECT e FROM Events e WHERE e.id = :id"),
    @NamedQuery(name = "Events.findByName", query = "SELECT e FROM Events e WHERE e.name = :name"),
    @NamedQuery(name = "Events.findByDescription", query = "SELECT e FROM Events e WHERE e.description = :description"),
    @NamedQuery(name = "Events.findByStartdate", query = "SELECT e FROM Events e WHERE e.startdate = :startdate"),
    @NamedQuery(name = "Events.findByEnddate", query = "SELECT e FROM Events e WHERE e.enddate = :enddate"),
    @NamedQuery(name = "Events.findByOutdoor", query = "SELECT e FROM Events e WHERE e.outdoor = :outdoor"),
    @NamedQuery(name = "Events.findByPrivacy", query = "SELECT e FROM Events e WHERE e.privacy = :privacy"),
    @NamedQuery(name = "Events.findByCity", query = "SELECT e FROM Events e WHERE e.city = :city"),
    @NamedQuery(name = "Events.findByAddress", query = "SELECT e FROM Events e WHERE e.address = :address"),
    @NamedQuery(name = "Events.expiredEvents", query = "SELECT e FROM Events e WHERE e.enddate < :enddate")})
public class Events implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 45)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIVACY")
    private boolean privacy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OUTDOOR")
    private boolean outdoor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CITY")
    private String city;
    @Size(max = 45)
    @Column(name = "ADDRESS")
    private String address;
    @OneToMany(mappedBy = "eventid")
    private Collection<Notification> notificationCollection;
    private static final long serialVersionUID = 1L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "events")
    private Collection<InvitationList> invitationListCollection;
    @JoinColumn(name = "ORGANIZER", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private User organizer;

    public Events() {
    }

    public Events(Date startdate, Date enddate) {
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Events(Date startdate, Date enddate, boolean outdoor, boolean privacy, String city, User organizer) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.outdoor = outdoor;
        this.privacy = privacy;
        this.city = city;
        this.organizer = organizer;
    }

    public Events(String name, String description, Date startdate, Date enddate, boolean outdoor, boolean privacy, String city, String address, User organizer) {
        this.name = name;
        this.description = description;
        this.startdate = startdate;
        this.enddate = enddate;
        this.outdoor = outdoor;
        this.privacy = privacy;
        this.city = city;
        this.address = address;
        this.organizer = organizer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }


    public boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public Collection<InvitationList> getInvitationListCollection() {
        return invitationListCollection;
    }

    public void setInvitationListCollection(Collection<InvitationList> invitationListCollection) {
        this.invitationListCollection = invitationListCollection;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Events)) {
            return false;
        }
        Events other = (Events) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.meteocal.entity.Events[ id=" + id + " ]";
    }


    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    public boolean getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }
   
}
