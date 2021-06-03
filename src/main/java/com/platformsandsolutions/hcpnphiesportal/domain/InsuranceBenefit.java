package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InsuranceBenefit.
 */
@Entity
@Table(name = "insurance_benefit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InsuranceBenefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "allowed")
    private String allowed;

    @Column(name = "used")
    private String used;

    @ManyToOne
    @JsonIgnoreProperties(value = { "benefits", "responseInsurance" }, allowSetters = true)
    private ResponseInsuranceItem responseInsuranceItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsuranceBenefit id(Long id) {
        this.id = id;
        return this;
    }

    public String getAllowed() {
        return this.allowed;
    }

    public InsuranceBenefit allowed(String allowed) {
        this.allowed = allowed;
        return this;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public String getUsed() {
        return this.used;
    }

    public InsuranceBenefit used(String used) {
        this.used = used;
        return this;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public ResponseInsuranceItem getResponseInsuranceItem() {
        return this.responseInsuranceItem;
    }

    public InsuranceBenefit responseInsuranceItem(ResponseInsuranceItem responseInsuranceItem) {
        this.setResponseInsuranceItem(responseInsuranceItem);
        return this;
    }

    public void setResponseInsuranceItem(ResponseInsuranceItem responseInsuranceItem) {
        this.responseInsuranceItem = responseInsuranceItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InsuranceBenefit)) {
            return false;
        }
        return id != null && id.equals(((InsuranceBenefit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InsuranceBenefit{" +
            "id=" + getId() +
            ", allowed='" + getAllowed() + "'" +
            ", used='" + getUsed() + "'" +
            "}";
    }
}
