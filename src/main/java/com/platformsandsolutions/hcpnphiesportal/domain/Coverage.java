package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CoverageTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.RelationShipEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Coverage.
 */
@Entity
@Table(name = "coverage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Coverage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "force_id")
    private String forceId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "coverage_type", nullable = false)
    private CoverageTypeEnum coverageType;

    @Column(name = "subscriber_id")
    private String subscriberId;

    @Column(name = "dependent")
    private String dependent;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relation_ship", nullable = false)
    private RelationShipEnum relationShip;

    @Column(name = "network")
    private String network;

    @Column(name = "subrogation")
    private Boolean subrogation;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient subscriberPatient;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient beneficiary;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization payor;

    @OneToMany(mappedBy = "coverage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coverage" }, allowSetters = true)
    private Set<ClassComponent> classComponents = new HashSet<>();

    @OneToMany(mappedBy = "coverage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exceptions", "coverage" }, allowSetters = true)
    private Set<CostToBeneficiaryComponent> costToBeneficiaryComponents = new HashSet<>();

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

    public Coverage id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Coverage guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getForceId() {
        return this.forceId;
    }

    public Coverage forceId(String forceId) {
        this.forceId = forceId;
        return this;
    }

    public void setForceId(String forceId) {
        this.forceId = forceId;
    }

    public CoverageTypeEnum getCoverageType() {
        return this.coverageType;
    }

    public Coverage coverageType(CoverageTypeEnum coverageType) {
        this.coverageType = coverageType;
        return this;
    }

    public void setCoverageType(CoverageTypeEnum coverageType) {
        this.coverageType = coverageType;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public Coverage subscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getDependent() {
        return this.dependent;
    }

    public Coverage dependent(String dependent) {
        this.dependent = dependent;
        return this;
    }

    public void setDependent(String dependent) {
        this.dependent = dependent;
    }

    public RelationShipEnum getRelationShip() {
        return this.relationShip;
    }

    public Coverage relationShip(RelationShipEnum relationShip) {
        this.relationShip = relationShip;
        return this;
    }

    public void setRelationShip(RelationShipEnum relationShip) {
        this.relationShip = relationShip;
    }

    public String getNetwork() {
        return this.network;
    }

    public Coverage network(String network) {
        this.network = network;
        return this;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Boolean getSubrogation() {
        return this.subrogation;
    }

    public Coverage subrogation(Boolean subrogation) {
        this.subrogation = subrogation;
        return this;
    }

    public void setSubrogation(Boolean subrogation) {
        this.subrogation = subrogation;
    }

    public Patient getSubscriberPatient() {
        return this.subscriberPatient;
    }

    public Coverage subscriberPatient(Patient patient) {
        this.setSubscriberPatient(patient);
        return this;
    }

    public void setSubscriberPatient(Patient patient) {
        this.subscriberPatient = patient;
    }

    public Patient getBeneficiary() {
        return this.beneficiary;
    }

    public Coverage beneficiary(Patient patient) {
        this.setBeneficiary(patient);
        return this;
    }

    public void setBeneficiary(Patient patient) {
        this.beneficiary = patient;
    }

    public Organization getPayor() {
        return this.payor;
    }

    public Coverage payor(Organization organization) {
        this.setPayor(organization);
        return this;
    }

    public void setPayor(Organization organization) {
        this.payor = organization;
    }

    public Set<ClassComponent> getClassComponents() {
        return this.classComponents;
    }

    public Coverage classComponents(Set<ClassComponent> classComponents) {
        this.setClassComponents(classComponents);
        return this;
    }

    public Coverage addClassComponents(ClassComponent classComponent) {
        this.classComponents.add(classComponent);
        classComponent.setCoverage(this);
        return this;
    }

    public Coverage removeClassComponents(ClassComponent classComponent) {
        this.classComponents.remove(classComponent);
        classComponent.setCoverage(null);
        return this;
    }

    public void setClassComponents(Set<ClassComponent> classComponents) {
        if (this.classComponents != null) {
            this.classComponents.forEach(i -> i.setCoverage(null));
        }
        if (classComponents != null) {
            classComponents.forEach(i -> i.setCoverage(this));
        }
        this.classComponents = classComponents;
    }

    public Set<CostToBeneficiaryComponent> getCostToBeneficiaryComponents() {
        return this.costToBeneficiaryComponents;
    }

    public Coverage costToBeneficiaryComponents(Set<CostToBeneficiaryComponent> costToBeneficiaryComponents) {
        this.setCostToBeneficiaryComponents(costToBeneficiaryComponents);
        return this;
    }

    public Coverage addCostToBeneficiaryComponents(CostToBeneficiaryComponent costToBeneficiaryComponent) {
        this.costToBeneficiaryComponents.add(costToBeneficiaryComponent);
        costToBeneficiaryComponent.setCoverage(this);
        return this;
    }

    public Coverage removeCostToBeneficiaryComponents(CostToBeneficiaryComponent costToBeneficiaryComponent) {
        this.costToBeneficiaryComponents.remove(costToBeneficiaryComponent);
        costToBeneficiaryComponent.setCoverage(null);
        return this;
    }

    public void setCostToBeneficiaryComponents(Set<CostToBeneficiaryComponent> costToBeneficiaryComponents) {
        if (this.costToBeneficiaryComponents != null) {
            this.costToBeneficiaryComponents.forEach(i -> i.setCoverage(null));
        }
        if (costToBeneficiaryComponents != null) {
            costToBeneficiaryComponents.forEach(i -> i.setCoverage(this));
        }
        this.costToBeneficiaryComponents = costToBeneficiaryComponents;
    }

    public CoverageEligibilityRequest getCoverageEligibilityRequest() {
        return this.coverageEligibilityRequest;
    }

    public Coverage coverageEligibilityRequest(CoverageEligibilityRequest coverageEligibilityRequest) {
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
        if (!(o instanceof Coverage)) {
            return false;
        }
        return id != null && id.equals(((Coverage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coverage{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", forceId='" + getForceId() + "'" +
            ", coverageType='" + getCoverageType() + "'" +
            ", subscriberId='" + getSubscriberId() + "'" +
            ", dependent='" + getDependent() + "'" +
            ", relationShip='" + getRelationShip() + "'" +
            ", network='" + getNetwork() + "'" +
            ", subrogation='" + getSubrogation() + "'" +
            "}";
    }
}
