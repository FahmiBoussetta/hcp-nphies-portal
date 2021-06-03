package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Insurance.
 */
@Entity
@Table(name = "insurance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Insurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NotNull
    @Column(name = "focal", nullable = false)
    private Boolean focal;

    @Column(name = "pre_auth_ref")
    private String preAuthRef;

    @JsonIgnoreProperties(
        value = {
            "subscriberPatient", "beneficiary", "payor", "classComponents", "costToBeneficiaryComponents", "coverageEligibilityRequest",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Coverage coverage;

    @JsonIgnoreProperties(value = { "errors", "items", "totals" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ClaimResponse claimResponse;

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

    public Insurance id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public Insurance sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getFocal() {
        return this.focal;
    }

    public Insurance focal(Boolean focal) {
        this.focal = focal;
        return this;
    }

    public void setFocal(Boolean focal) {
        this.focal = focal;
    }

    public String getPreAuthRef() {
        return this.preAuthRef;
    }

    public Insurance preAuthRef(String preAuthRef) {
        this.preAuthRef = preAuthRef;
        return this;
    }

    public void setPreAuthRef(String preAuthRef) {
        this.preAuthRef = preAuthRef;
    }

    public Coverage getCoverage() {
        return this.coverage;
    }

    public Insurance coverage(Coverage coverage) {
        this.setCoverage(coverage);
        return this;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public ClaimResponse getClaimResponse() {
        return this.claimResponse;
    }

    public Insurance claimResponse(ClaimResponse claimResponse) {
        this.setClaimResponse(claimResponse);
        return this;
    }

    public void setClaimResponse(ClaimResponse claimResponse) {
        this.claimResponse = claimResponse;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public Insurance claim(Claim claim) {
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
        if (!(o instanceof Insurance)) {
            return false;
        }
        return id != null && id.equals(((Insurance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Insurance{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            ", focal='" + getFocal() + "'" +
            ", preAuthRef='" + getPreAuthRef() + "'" +
            "}";
    }
}
