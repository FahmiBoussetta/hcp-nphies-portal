package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CovEliRespErrorMessages.
 */
@Entity
@Table(name = "cov_eli_resp_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CovEliRespErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(value = { "patient", "insurer", "errors", "insurances" }, allowSetters = true)
    private CoverageEligibilityResponse coverageEligibilityResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CovEliRespErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public CovEliRespErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CoverageEligibilityResponse getCoverageEligibilityResponse() {
        return this.coverageEligibilityResponse;
    }

    public CovEliRespErrorMessages coverageEligibilityResponse(CoverageEligibilityResponse coverageEligibilityResponse) {
        this.setCoverageEligibilityResponse(coverageEligibilityResponse);
        return this;
    }

    public void setCoverageEligibilityResponse(CoverageEligibilityResponse coverageEligibilityResponse) {
        this.coverageEligibilityResponse = coverageEligibilityResponse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CovEliRespErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((CovEliRespErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CovEliRespErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
