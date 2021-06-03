package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PriorityEnum;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CoverageEligibilityRequest.
 */
@Entity
@Table(name = "coverage_eligibility_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoverageEligibilityRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "parsed")
    private String parsed;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private PriorityEnum priority;

    @Column(name = "identifier")
    private String identifier;

    @NotNull
    @Column(name = "serviced_date", nullable = false)
    private Instant servicedDate;

    @Column(name = "serviced_date_end")
    private Instant servicedDateEnd;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient patient;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization provider;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization insurer;

    @JsonIgnoreProperties(value = { "managingOrganization" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location facility;

    @OneToMany(mappedBy = "coverageEligibilityRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coverageEligibilityRequest" }, allowSetters = true)
    private Set<CovEliErrorMessages> errors = new HashSet<>();

    @OneToMany(mappedBy = "coverageEligibilityRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coverageEligibilityRequest" }, allowSetters = true)
    private Set<ListEligibilityPurposeEnum> purposes = new HashSet<>();

    @OneToMany(mappedBy = "coverageEligibilityRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "subscriberPatient", "beneficiary", "payor", "classComponents", "costToBeneficiaryComponents", "coverageEligibilityRequest",
        },
        allowSetters = true
    )
    private Set<Coverage> coverages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoverageEligibilityRequest id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public CoverageEligibilityRequest guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParsed() {
        return this.parsed;
    }

    public CoverageEligibilityRequest parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public PriorityEnum getPriority() {
        return this.priority;
    }

    public CoverageEligibilityRequest priority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public CoverageEligibilityRequest identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Instant getServicedDate() {
        return this.servicedDate;
    }

    public CoverageEligibilityRequest servicedDate(Instant servicedDate) {
        this.servicedDate = servicedDate;
        return this;
    }

    public void setServicedDate(Instant servicedDate) {
        this.servicedDate = servicedDate;
    }

    public Instant getServicedDateEnd() {
        return this.servicedDateEnd;
    }

    public CoverageEligibilityRequest servicedDateEnd(Instant servicedDateEnd) {
        this.servicedDateEnd = servicedDateEnd;
        return this;
    }

    public void setServicedDateEnd(Instant servicedDateEnd) {
        this.servicedDateEnd = servicedDateEnd;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public CoverageEligibilityRequest patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Organization getProvider() {
        return this.provider;
    }

    public CoverageEligibilityRequest provider(Organization organization) {
        this.setProvider(organization);
        return this;
    }

    public void setProvider(Organization organization) {
        this.provider = organization;
    }

    public Organization getInsurer() {
        return this.insurer;
    }

    public CoverageEligibilityRequest insurer(Organization organization) {
        this.setInsurer(organization);
        return this;
    }

    public void setInsurer(Organization organization) {
        this.insurer = organization;
    }

    public Location getFacility() {
        return this.facility;
    }

    public CoverageEligibilityRequest facility(Location location) {
        this.setFacility(location);
        return this;
    }

    public void setFacility(Location location) {
        this.facility = location;
    }

    public Set<CovEliErrorMessages> getErrors() {
        return this.errors;
    }

    public CoverageEligibilityRequest errors(Set<CovEliErrorMessages> covEliErrorMessages) {
        this.setErrors(covEliErrorMessages);
        return this;
    }

    public CoverageEligibilityRequest addErrors(CovEliErrorMessages covEliErrorMessages) {
        this.errors.add(covEliErrorMessages);
        covEliErrorMessages.setCoverageEligibilityRequest(this);
        return this;
    }

    public CoverageEligibilityRequest removeErrors(CovEliErrorMessages covEliErrorMessages) {
        this.errors.remove(covEliErrorMessages);
        covEliErrorMessages.setCoverageEligibilityRequest(null);
        return this;
    }

    public void setErrors(Set<CovEliErrorMessages> covEliErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setCoverageEligibilityRequest(null));
        }
        if (covEliErrorMessages != null) {
            covEliErrorMessages.forEach(i -> i.setCoverageEligibilityRequest(this));
        }
        this.errors = covEliErrorMessages;
    }

    public Set<ListEligibilityPurposeEnum> getPurposes() {
        return this.purposes;
    }

    public CoverageEligibilityRequest purposes(Set<ListEligibilityPurposeEnum> listEligibilityPurposeEnums) {
        this.setPurposes(listEligibilityPurposeEnums);
        return this;
    }

    public CoverageEligibilityRequest addPurposes(ListEligibilityPurposeEnum listEligibilityPurposeEnum) {
        this.purposes.add(listEligibilityPurposeEnum);
        listEligibilityPurposeEnum.setCoverageEligibilityRequest(this);
        return this;
    }

    public CoverageEligibilityRequest removePurposes(ListEligibilityPurposeEnum listEligibilityPurposeEnum) {
        this.purposes.remove(listEligibilityPurposeEnum);
        listEligibilityPurposeEnum.setCoverageEligibilityRequest(null);
        return this;
    }

    public void setPurposes(Set<ListEligibilityPurposeEnum> listEligibilityPurposeEnums) {
        if (this.purposes != null) {
            this.purposes.forEach(i -> i.setCoverageEligibilityRequest(null));
        }
        if (listEligibilityPurposeEnums != null) {
            listEligibilityPurposeEnums.forEach(i -> i.setCoverageEligibilityRequest(this));
        }
        this.purposes = listEligibilityPurposeEnums;
    }

    public Set<Coverage> getCoverages() {
        return this.coverages;
    }

    public CoverageEligibilityRequest coverages(Set<Coverage> coverages) {
        this.setCoverages(coverages);
        return this;
    }

    public CoverageEligibilityRequest addCoverages(Coverage coverage) {
        this.coverages.add(coverage);
        coverage.setCoverageEligibilityRequest(this);
        return this;
    }

    public CoverageEligibilityRequest removeCoverages(Coverage coverage) {
        this.coverages.remove(coverage);
        coverage.setCoverageEligibilityRequest(null);
        return this;
    }

    public void setCoverages(Set<Coverage> coverages) {
        if (this.coverages != null) {
            this.coverages.forEach(i -> i.setCoverageEligibilityRequest(null));
        }
        if (coverages != null) {
            coverages.forEach(i -> i.setCoverageEligibilityRequest(this));
        }
        this.coverages = coverages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoverageEligibilityRequest)) {
            return false;
        }
        return id != null && id.equals(((CoverageEligibilityRequest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoverageEligibilityRequest{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", priority='" + getPriority() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", servicedDate='" + getServicedDate() + "'" +
            ", servicedDateEnd='" + getServicedDateEnd() + "'" +
            "}";
    }
}
