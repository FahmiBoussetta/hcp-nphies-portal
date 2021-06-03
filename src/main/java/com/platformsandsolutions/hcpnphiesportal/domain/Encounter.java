package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ActPriorityEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.EncounterClassEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ServiceTypeEnum;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Encounter.
 */
@Entity
@Table(name = "encounter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Encounter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "force_id")
    private String forceId;

    @Column(name = "identifier")
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "encounter_class")
    private EncounterClassEnum encounterClass;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceTypeEnum serviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private ActPriorityEnum priority;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient subject;

    @JsonIgnoreProperties(value = { "origin" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Hospitalization hospitalization;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization serviceProvider;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Encounter id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Encounter guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getForceId() {
        return this.forceId;
    }

    public Encounter forceId(String forceId) {
        this.forceId = forceId;
        return this;
    }

    public void setForceId(String forceId) {
        this.forceId = forceId;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Encounter identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public EncounterClassEnum getEncounterClass() {
        return this.encounterClass;
    }

    public Encounter encounterClass(EncounterClassEnum encounterClass) {
        this.encounterClass = encounterClass;
        return this;
    }

    public void setEncounterClass(EncounterClassEnum encounterClass) {
        this.encounterClass = encounterClass;
    }

    public Instant getStart() {
        return this.start;
    }

    public Encounter start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return this.end;
    }

    public Encounter end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public ServiceTypeEnum getServiceType() {
        return this.serviceType;
    }

    public Encounter serviceType(ServiceTypeEnum serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(ServiceTypeEnum serviceType) {
        this.serviceType = serviceType;
    }

    public ActPriorityEnum getPriority() {
        return this.priority;
    }

    public Encounter priority(ActPriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(ActPriorityEnum priority) {
        this.priority = priority;
    }

    public Patient getSubject() {
        return this.subject;
    }

    public Encounter subject(Patient patient) {
        this.setSubject(patient);
        return this;
    }

    public void setSubject(Patient patient) {
        this.subject = patient;
    }

    public Hospitalization getHospitalization() {
        return this.hospitalization;
    }

    public Encounter hospitalization(Hospitalization hospitalization) {
        this.setHospitalization(hospitalization);
        return this;
    }

    public void setHospitalization(Hospitalization hospitalization) {
        this.hospitalization = hospitalization;
    }

    public Organization getServiceProvider() {
        return this.serviceProvider;
    }

    public Encounter serviceProvider(Organization organization) {
        this.setServiceProvider(organization);
        return this;
    }

    public void setServiceProvider(Organization organization) {
        this.serviceProvider = organization;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Encounter)) {
            return false;
        }
        return id != null && id.equals(((Encounter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Encounter{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", forceId='" + getForceId() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", encounterClass='" + getEncounterClass() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
