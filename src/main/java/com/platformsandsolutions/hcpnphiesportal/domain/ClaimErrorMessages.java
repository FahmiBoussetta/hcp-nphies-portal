package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClaimErrorMessages.
 */
@Entity
@Table(name = "claim_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClaimErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
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
    private Claim claim;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClaimErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ClaimErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public ClaimErrorMessages claim(Claim claim) {
        this.setClaim(claim);
        return this;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((ClaimErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
