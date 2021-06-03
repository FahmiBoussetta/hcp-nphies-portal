package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CovEliErrorMessages.
 */
@Entity
@Table(name = "cov_eli_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CovEliErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(value = { "patient", "provider", "insurer", "facility", "errors", "purposes", "coverages" }, allowSetters = true)
    private CoverageEligibilityRequest coverageEligibilityRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CovEliErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public CovEliErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CoverageEligibilityRequest getCoverageEligibilityRequest() {
        return this.coverageEligibilityRequest;
    }

    public CovEliErrorMessages coverageEligibilityRequest(CoverageEligibilityRequest coverageEligibilityRequest) {
        this.setCoverageEligibilityRequest(coverageEligibilityRequest);
        return this;
    }

    public void setCoverageEligibilityRequest(CoverageEligibilityRequest coverageEligibilityRequest) {
        this.coverageEligibilityRequest = coverageEligibilityRequest;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CovEliErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((CovEliErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CovEliErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
