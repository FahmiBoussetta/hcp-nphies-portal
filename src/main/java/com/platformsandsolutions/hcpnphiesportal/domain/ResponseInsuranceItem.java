package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResponseInsuranceItem.
 */
@Entity
@Table(name = "response_insurance_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResponseInsuranceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "excluded")
    private Boolean excluded;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "network")
    private String network;

    @Column(name = "unit")
    private String unit;

    @Column(name = "term")
    private String term;

    @OneToMany(mappedBy = "responseInsuranceItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "responseInsuranceItem" }, allowSetters = true)
    private Set<InsuranceBenefit> benefits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "coverage", "items", "coverageEligibilityResponse" }, allowSetters = true)
    private ResponseInsurance responseInsurance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponseInsuranceItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public ResponseInsuranceItem category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getExcluded() {
        return this.excluded;
    }

    public ResponseInsuranceItem excluded(Boolean excluded) {
        this.excluded = excluded;
        return this;
    }

    public void setExcluded(Boolean excluded) {
        this.excluded = excluded;
    }

    public String getName() {
        return this.name;
    }

    public ResponseInsuranceItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ResponseInsuranceItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNetwork() {
        return this.network;
    }

    public ResponseInsuranceItem network(String network) {
        this.network = network;
        return this;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getUnit() {
        return this.unit;
    }

    public ResponseInsuranceItem unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTerm() {
        return this.term;
    }

    public ResponseInsuranceItem term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Set<InsuranceBenefit> getBenefits() {
        return this.benefits;
    }

    public ResponseInsuranceItem benefits(Set<InsuranceBenefit> insuranceBenefits) {
        this.setBenefits(insuranceBenefits);
        return this;
    }

    public ResponseInsuranceItem addBenefit(InsuranceBenefit insuranceBenefit) {
        this.benefits.add(insuranceBenefit);
        insuranceBenefit.setResponseInsuranceItem(this);
        return this;
    }

    public ResponseInsuranceItem removeBenefit(InsuranceBenefit insuranceBenefit) {
        this.benefits.remove(insuranceBenefit);
        insuranceBenefit.setResponseInsuranceItem(null);
        return this;
    }

    public void setBenefits(Set<InsuranceBenefit> insuranceBenefits) {
        if (this.benefits != null) {
            this.benefits.forEach(i -> i.setResponseInsuranceItem(null));
        }
        if (insuranceBenefits != null) {
            insuranceBenefits.forEach(i -> i.setResponseInsuranceItem(this));
        }
        this.benefits = insuranceBenefits;
    }

    public ResponseInsurance getResponseInsurance() {
        return this.responseInsurance;
    }

    public ResponseInsuranceItem responseInsurance(ResponseInsurance responseInsurance) {
        this.setResponseInsurance(responseInsurance);
        return this;
    }

    public void setResponseInsurance(ResponseInsurance responseInsurance) {
        this.responseInsurance = responseInsurance;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseInsuranceItem)) {
            return false;
        }
        return id != null && id.equals(((ResponseInsuranceItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponseInsuranceItem{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", excluded='" + getExcluded() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", network='" + getNetwork() + "'" +
            ", unit='" + getUnit() + "'" +
            ", term='" + getTerm() + "'" +
            "}";
    }
}
