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
 * A ResponseInsurance.
 */
@Entity
@Table(name = "response_insurance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResponseInsurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "not_inforce_reason")
    private String notInforceReason;

    @Column(name = "inforce")
    private Boolean inforce;

    @Column(name = "benefit_start")
    private Instant benefitStart;

    @Column(name = "benefit_end")
    private Instant benefitEnd;

    @JsonIgnoreProperties(
        value = {
            "subscriberPatient", "beneficiary", "payor", "classComponents", "costToBeneficiaryComponents", "coverageEligibilityRequests",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Coverage coverage;

    @OneToMany(mappedBy = "responseInsurance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefits", "responseInsurance" }, allowSetters = true)
    private Set<ResponseInsuranceItem> items = new HashSet<>();

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

    public ResponseInsurance id(Long id) {
        this.id = id;
        return this;
    }

    public String getNotInforceReason() {
        return this.notInforceReason;
    }

    public ResponseInsurance notInforceReason(String notInforceReason) {
        this.notInforceReason = notInforceReason;
        return this;
    }

    public void setNotInforceReason(String notInforceReason) {
        this.notInforceReason = notInforceReason;
    }

    public Boolean getInforce() {
        return this.inforce;
    }

    public ResponseInsurance inforce(Boolean inforce) {
        this.inforce = inforce;
        return this;
    }

    public void setInforce(Boolean inforce) {
        this.inforce = inforce;
    }

    public Instant getBenefitStart() {
        return this.benefitStart;
    }

    public ResponseInsurance benefitStart(Instant benefitStart) {
        this.benefitStart = benefitStart;
        return this;
    }

    public void setBenefitStart(Instant benefitStart) {
        this.benefitStart = benefitStart;
    }

    public Instant getBenefitEnd() {
        return this.benefitEnd;
    }

    public ResponseInsurance benefitEnd(Instant benefitEnd) {
        this.benefitEnd = benefitEnd;
        return this;
    }

    public void setBenefitEnd(Instant benefitEnd) {
        this.benefitEnd = benefitEnd;
    }

    public Coverage getCoverage() {
        return this.coverage;
    }

    public ResponseInsurance coverage(Coverage coverage) {
        this.setCoverage(coverage);
        return this;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public Set<ResponseInsuranceItem> getItems() {
        return this.items;
    }

    public ResponseInsurance items(Set<ResponseInsuranceItem> responseInsuranceItems) {
        this.setItems(responseInsuranceItems);
        return this;
    }

    public ResponseInsurance addItem(ResponseInsuranceItem responseInsuranceItem) {
        this.items.add(responseInsuranceItem);
        responseInsuranceItem.setResponseInsurance(this);
        return this;
    }

    public ResponseInsurance removeItem(ResponseInsuranceItem responseInsuranceItem) {
        this.items.remove(responseInsuranceItem);
        responseInsuranceItem.setResponseInsurance(null);
        return this;
    }

    public void setItems(Set<ResponseInsuranceItem> responseInsuranceItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setResponseInsurance(null));
        }
        if (responseInsuranceItems != null) {
            responseInsuranceItems.forEach(i -> i.setResponseInsurance(this));
        }
        this.items = responseInsuranceItems;
    }

    public CoverageEligibilityResponse getCoverageEligibilityResponse() {
        return this.coverageEligibilityResponse;
    }

    public ResponseInsurance coverageEligibilityResponse(CoverageEligibilityResponse coverageEligibilityResponse) {
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
        if (!(o instanceof ResponseInsurance)) {
            return false;
        }
        return id != null && id.equals(((ResponseInsurance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponseInsurance{" +
            "id=" + getId() +
            ", notInforceReason='" + getNotInforceReason() + "'" +
            ", inforce='" + getInforce() + "'" +
            ", benefitStart='" + getBenefitStart() + "'" +
            ", benefitEnd='" + getBenefitEnd() + "'" +
            "}";
    }
}
