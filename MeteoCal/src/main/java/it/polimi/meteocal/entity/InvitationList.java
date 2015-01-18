/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.meteocal.entity;

import it.polimi.registration.business.security.entity.User;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jiasheng
 */
@Entity
@Table(name = "INVITATION_LIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvitationList.findAll", query = "SELECT i FROM InvitationList i"),
    @NamedQuery(name= "findAllInvitationListWithEventId", query="SELECT i FROM InvitationList i WHERE i.invitationListPK.eventid LIKE :eventid"),
    @NamedQuery(name= "findAllParticipatingListWithEventId", query="SELECT i FROM InvitationList i WHERE i.invitationListPK.eventid LIKE :eventid AND i.participate LIKE :participate"),
    @NamedQuery(name= "findUserWithEventId", query="SELECT i FROM InvitationList i WHERE i.invitationListPK.eventid LIKE :eventid AND i.invitationListPK.user LIKE :user"),
    @NamedQuery(name = "InvitationList.findByUser", query = "SELECT i FROM InvitationList i WHERE i.invitationListPK.user = :user"),
    @NamedQuery(name = "InvitationList.findByEventid", query = "SELECT i FROM InvitationList i WHERE i.invitationListPK.eventid = :eventid"),
    @NamedQuery(name = "InvitationList.findByParticipate", query = "SELECT i FROM InvitationList i WHERE i.participate = :participate")})
public class InvitationList implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvitationListPK invitationListPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARTICIPATE")
    private boolean participate;
    @JoinColumn(name = "USER", referencedColumnName = "EMAIL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;
    @JoinColumn(name = "EVENTID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Events events;

    public InvitationList() {
    }

    public InvitationList(InvitationListPK invitationListPK) {
        this.invitationListPK = invitationListPK;
    }

    public InvitationList(InvitationListPK invitationListPK, boolean participate) {
        this.invitationListPK = invitationListPK;
        this.participate = participate;
    }

    public InvitationList(String user, int eventid) {
        this.invitationListPK = new InvitationListPK(user, eventid);
    }

    public InvitationList(User user1, Events events) {
        this.user1 = user1;
        this.events = events;
    }

    public InvitationListPK getInvitationListPK() {
        return invitationListPK;
    }

    public void setInvitationListPK(InvitationListPK invitationListPK) {
        this.invitationListPK = invitationListPK;
    }

    public boolean getParticipate() {
        return participate;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invitationListPK != null ? invitationListPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvitationList)) {
            return false;
        }
        InvitationList other = (InvitationList) object;
        if ((this.invitationListPK == null && other.invitationListPK != null) || (this.invitationListPK != null && !this.invitationListPK.equals(other.invitationListPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.meteocal.entity.InvitationList[ invitationListPK=" + invitationListPK + " ]";
    }
    
}
