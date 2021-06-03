package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CoverageEligibilityResponse.
 */
@Entity
@Table(name = "coverage_eligibility_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoverageEligibilityResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "serviced")
    private Instant serviced;

    @Column(name = "serviced_end")
    private Instant servicedEnd;

    @Column(name = "disposition")
    private String disposition;

    @Column(name = "not_inforce_reason")
    private String notInforceReason;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient patient;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization insurer;

    @OneToMany(mappedBy = "coverageEligibilityResponse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coverageEligibilityResponse" }, allowSetters = true)
    private Set<CovEliRespErrorMessages> errors = new HashSet<>();

    @OneToMany(mappedBy = "coverageEligibilityResponse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coverage", "items", "coverageEligibilityResponse" }, allowSetters = true)
    private Set<ResponseInsurance> insurances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoverageEligibilityResponse id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public CoverageEligibilityResponse value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSystem() {
        return this.system;
    }

    public CoverageEligibilityResponse system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getParsed() {
        return this.parsed;
    }

    public CoverageEligibilityResponse parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getOutcome() {
        return this.outcome;
    }

    public CoverageEligibilityResponse outcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Instant getServiced() {
        return this.serviced;
    }

    public CoverageEligibilityResponse serviced(Instant serviced) {
        this.serviced = serviced;
        return this;
    }

    public void setServiced(Instant serviced) {
        this.serviced = serviced;
    }

    public Instant getServicedEnd() {
        return this.servicedEnd;
    }

    public CoverageEligibilityResponse servicedEnd(Instant servicedEnd) {
        this.servicedEnd = servicedEnd;
        return this;
    }

    public void setServicedEnd(Instant servicedEnd) {
        this.servicedEnd = servicedEnd;
    }

    public String getDisposition() {
        return this.disposition;
    }

    public CoverageEligibilityResponse disposition(String disposition) {
        this.disposition = disposition;
        return this;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public String getNotInforceReason() {
        return this.notInforceReason;
    }

    public CoverageEligibilityResponse notInforceReason(String notInforceReason) {
        this.notInforceReason = notInforceReason;
        return this;
    }

    public void setNotInforceReason(String notInforceReason) {
        this.notInforceReason = notInforceReason;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public CoverageEligibilityResponse patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Organization getInsurer() {
        return this.insurer;
    }

    public CoverageEligibilityResponse insurer(Organization organization) {
        this.setInsurer(organization);
        return this;
    }

    public void setInsurer(Organization organization) {
        this.insurer = organization;
    }

    public Set<CovEliRespErrorMessages> getErrors() {
        return this.errors;
    }

    public CoverageEligibilityResponse errors(Set<CovEliRespErrorMessages> covEliRespErrorMessages) {
        this.setErrors(covEliRespErrorMessages);
        return this;
    }

    public CoverageEligibilityResponse addErrors(CovEliRespErrorMessages covEliRespErrorMessages) {
        this.errors.add(covEliRespErrorMessages);
        covEliRespErrorMessages.setCoverageEligibilityResponse(this);
        return this;
    }

    public CoverageEligibilityResponse removeErrors(CovEliRespErrorMessages covEliRespErrorMessages) {
        this.errors.remove(covEliRespErrorMessages);
        covEliRespErrorMessages.setCoverageEligibilityResponse(null);
        return this;
    }

    public void setErrors(Set<CovEliRespErrorMessages> covEliRespErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setCoverageEligibilityResponse(null));
        }
        if (covEliRespErrorMessages != null) {
            covEliRespErrorMessages.forEach(i -> i.setCoverageEligibilityResponse(this));
        }
        this.errors = covEliRespErrorMessages;
    }

    public Set<ResponseInsurance> getInsurances() {
        return this.insurances;
    }

    public CoverageEligibilityResponse insurances(Set<ResponseInsurance> responseInsurances) {
        this.setInsurances(responseInsurances);
        return this;
    }

    public CoverageEligibilityResponse addInsurance(ResponseInsurance responseInsurance) {
        this.insurances.add(responseInsurance);
        responseInsurance.setCoverageEligibilityResponse(this);
        return this;
    }

    public CoverageEligibilityResponse removeInsurance(ResponseInsurance responseInsurance) {
        this.insurances.remove(responseInsurance);
        responseInsurance.setCoverageEligibilityResponse(null);
        return this;
    }

    public void setInsurances(Set<ResponseInsurance> responseInsurances) {
        if (this.insurances != null) {
            this.insurances.forEach(i -> i.setCoverageEligibilityResponse(null));
        }
        if (responseInsurances != null) {
            responseInsurances.forEach(i -> i.setCoverageEligibilityResponse(this));
        }
        this.insurances = responseInsurances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoverageEligibilityResponse)) {
            return false;
        }
        return id != null && id.equals(((CoverageEligibilityResponse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoverageEligibilityResponse{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", system='" + getSystem() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", outcome='" + getOutcome() + "'" +
            ", serviced='" + getServiced() + "'" +
            ", servicedEnd='" + getServicedEnd() + "'" +
            ", disposition='" + getDisposition() + "'" +
            ", notInforceReason='" + getNotInforceReason() + "'" +
            "}";
    }
}
