package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CommunicationPriorityEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Communication.
 */
@Entity
@Table(name = "communication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Communication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "is_queued")
    private Boolean isQueued;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "identifier")
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private CommunicationPriorityEnum priority;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient subject;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization sender;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization recipient;

    @JsonIgnoreProperties(
        value = {
            "encounter",
            "eligibilityResponse",
            "patient",
            "provider",
            "insurer",
            "prescription",
            "originalPrescription",
            "payee",
            "referral",
            "facility",
            "accident",
            "diagnoses",
            "insurances",
            "items",
            "errors",
            "relateds",
            "careTeams",
            "supportingInfos",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Claim about;

    @OneToMany(mappedBy = "communication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subject", "about", "sender", "payloads", "notes", "communication" }, allowSetters = true)
    private Set<CommunicationRequest> basedOns = new HashSet<>();

    @OneToMany(mappedBy = "communication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "communication" }, allowSetters = true)
    private Set<ListCommunicationMediumEnum> mediums = new HashSet<>();

    @OneToMany(mappedBy = "communication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "communication" }, allowSetters = true)
    private Set<ListCommunicationReasonEnum> reasonCodes = new HashSet<>();

    @OneToMany(mappedBy = "communication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contentAttachment", "contentReference", "communication", "communicationRequest" }, allowSetters = true)
    private Set<Payload> payloads = new HashSet<>();

    @OneToMany(mappedBy = "communication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "communication", "communicationRequest" }, allowSetters = true)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "communication")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "communication" }, allowSetters = true)
    private Set<ComErrorMessages> errors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Communication id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Communication guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Boolean getIsQueued() {
        return this.isQueued;
    }

    public Communication isQueued(Boolean isQueued) {
        this.isQueued = isQueued;
        return this;
    }

    public void setIsQueued(Boolean isQueued) {
        this.isQueued = isQueued;
    }

    public String getParsed() {
        return this.parsed;
    }

    public Communication parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Communication identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public CommunicationPriorityEnum getPriority() {
        return this.priority;
    }

    public Communication priority(CommunicationPriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(CommunicationPriorityEnum priority) {
        this.priority = priority;
    }

    public Patient getSubject() {
        return this.subject;
    }

    public Communication subject(Patient patient) {
        this.setSubject(patient);
        return this;
    }

    public void setSubject(Patient patient) {
        this.subject = patient;
    }

    public Organization getSender() {
        return this.sender;
    }

    public Communication sender(Organization organization) {
        this.setSender(organization);
        return this;
    }

    public void setSender(Organization organization) {
        this.sender = organization;
    }

    public Organization getRecipient() {
        return this.recipient;
    }

    public Communication recipient(Organization organization) {
        this.setRecipient(organization);
        return this;
    }

    public void setRecipient(Organization organization) {
        this.recipient = organization;
    }

    public Claim getAbout() {
        return this.about;
    }

    public Communication about(Claim claim) {
        this.setAbout(claim);
        return this;
    }

    public void setAbout(Claim claim) {
        this.about = claim;
    }

    public Set<CommunicationRequest> getBasedOns() {
        return this.basedOns;
    }

    public Communication basedOns(Set<CommunicationRequest> communicationRequests) {
        this.setBasedOns(communicationRequests);
        return this;
    }

    public Communication addBasedOn(CommunicationRequest communicationRequest) {
        this.basedOns.add(communicationRequest);
        communicationRequest.setCommunication(this);
        return this;
    }

    public Communication removeBasedOn(CommunicationRequest communicationRequest) {
        this.basedOns.remove(communicationRequest);
        communicationRequest.setCommunication(null);
        return this;
    }

    public void setBasedOns(Set<CommunicationRequest> communicationRequests) {
        if (this.basedOns != null) {
            this.basedOns.forEach(i -> i.setCommunication(null));
        }
        if (communicationRequests != null) {
            communicationRequests.forEach(i -> i.setCommunication(this));
        }
        this.basedOns = communicationRequests;
    }

    public Set<ListCommunicationMediumEnum> getMediums() {
        return this.mediums;
    }

    public Communication mediums(Set<ListCommunicationMediumEnum> listCommunicationMediumEnums) {
        this.setMediums(listCommunicationMediumEnums);
        return this;
    }

    public Communication addMedium(ListCommunicationMediumEnum listCommunicationMediumEnum) {
        this.mediums.add(listCommunicationMediumEnum);
        listCommunicationMediumEnum.setCommunication(this);
        return this;
    }

    public Communication removeMedium(ListCommunicationMediumEnum listCommunicationMediumEnum) {
        this.mediums.remove(listCommunicationMediumEnum);
        listCommunicationMediumEnum.setCommunication(null);
        return this;
    }

    public void setMediums(Set<ListCommunicationMediumEnum> listCommunicationMediumEnums) {
        if (this.mediums != null) {
            this.mediums.forEach(i -> i.setCommunication(null));
        }
        if (listCommunicationMediumEnums != null) {
            listCommunicationMediumEnums.forEach(i -> i.setCommunication(this));
        }
        this.mediums = listCommunicationMediumEnums;
    }

    public Set<ListCommunicationReasonEnum> getReasonCodes() {
        return this.reasonCodes;
    }

    public Communication reasonCodes(Set<ListCommunicationReasonEnum> listCommunicationReasonEnums) {
        this.setReasonCodes(listCommunicationReasonEnums);
        return this;
    }

    public Communication addReasonCode(ListCommunicationReasonEnum listCommunicationReasonEnum) {
        this.reasonCodes.add(listCommunicationReasonEnum);
        listCommunicationReasonEnum.setCommunication(this);
        return this;
    }

    public Communication removeReasonCode(ListCommunicationReasonEnum listCommunicationReasonEnum) {
        this.reasonCodes.remove(listCommunicationReasonEnum);
        listCommunicationReasonEnum.setCommunication(null);
        return this;
    }

    public void setReasonCodes(Set<ListCommunicationReasonEnum> listCommunicationReasonEnums) {
        if (this.reasonCodes != null) {
            this.reasonCodes.forEach(i -> i.setCommunication(null));
        }
        if (listCommunicationReasonEnums != null) {
            listCommunicationReasonEnums.forEach(i -> i.setCommunication(this));
        }
        this.reasonCodes = listCommunicationReasonEnums;
    }

    public Set<Payload> getPayloads() {
        return this.payloads;
    }

    public Communication payloads(Set<Payload> payloads) {
        this.setPayloads(payloads);
        return this;
    }

    public Communication addPayload(Payload payload) {
        this.payloads.add(payload);
        payload.setCommunication(this);
        return this;
    }

    public Communication removePayload(Payload payload) {
        this.payloads.remove(payload);
        payload.setCommunication(null);
        return this;
    }

    public void setPayloads(Set<Payload> payloads) {
        if (this.payloads != null) {
            this.payloads.forEach(i -> i.setCommunication(null));
        }
        if (payloads != null) {
            payloads.forEach(i -> i.setCommunication(this));
        }
        this.payloads = payloads;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public Communication notes(Set<Note> notes) {
        this.setNotes(notes);
        return this;
    }

    public Communication addNote(Note note) {
        this.notes.add(note);
        note.setCommunication(this);
        return this;
    }

    public Communication removeNote(Note note) {
        this.notes.remove(note);
        note.setCommunication(null);
        return this;
    }

    public void setNotes(Set<Note> notes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setCommunication(null));
        }
        if (notes != null) {
            notes.forEach(i -> i.setCommunication(this));
        }
        this.notes = notes;
    }

    public Set<ComErrorMessages> getErrors() {
        return this.errors;
    }

    public Communication errors(Set<ComErrorMessages> comErrorMessages) {
        this.setErrors(comErrorMessages);
        return this;
    }

    public Communication addErrors(ComErrorMessages comErrorMessages) {
        this.errors.add(comErrorMessages);
        comErrorMessages.setCommunication(this);
        return this;
    }

    public Communication removeErrors(ComErrorMessages comErrorMessages) {
        this.errors.remove(comErrorMessages);
        comErrorMessages.setCommunication(null);
        return this;
    }

    public void setErrors(Set<ComErrorMessages> comErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setCommunication(null));
        }
        if (comErrorMessages != null) {
            comErrorMessages.forEach(i -> i.setCommunication(this));
        }
        this.errors = comErrorMessages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Communication)) {
            return false;
        }
        return id != null && id.equals(((Communication) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Communication{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", isQueued='" + getIsQueued() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
