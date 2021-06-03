package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClaimRelationshipEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Related.
 */
@Entity
@Table(name = "related")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Related implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "relation_ship")
    private ClaimRelationshipEnum relationShip;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier claimReference;

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

    public Related id(Long id) {
        this.id = id;
        return this;
    }

    public ClaimRelationshipEnum getRelationShip() {
        return this.relationShip;
    }

    public Related relationShip(ClaimRelationshipEnum relationShip) {
        this.relationShip = relationShip;
        return this;
    }

    public void setRelationShip(ClaimRelationshipEnum relationShip) {
        this.relationShip = relationShip;
    }

    public ReferenceIdentifier getClaimReference() {
        return this.claimReference;
    }

    public Related claimReference(ReferenceIdentifier referenceIdentifier) {
        this.setClaimReference(referenceIdentifier);
        return this;
    }

    public void setClaimReference(ReferenceIdentifier referenceIdentifier) {
        this.claimReference = referenceIdentifier;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public Related claim(Claim claim) {
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
        if (!(o instanceof Related)) {
            return false;
        }
        return id != null && id.equals(((Related) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Related{" +
            "id=" + getId() +
            ", relationShip='" + getRelationShip() + "'" +
            "}";
    }
}
