package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CareTeamRoleEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CareTeam.
 */
@Entity
@Table(name = "care_team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CareTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private CareTeamRoleEnum role;

    @JsonIgnoreProperties(value = { "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Practitioner provider;

    @JsonIgnoreProperties(value = { "practitioner", "organization", "codes", "specialties" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PractitionerRole providerRole;

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

    public CareTeam id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public CareTeam sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public CareTeamRoleEnum getRole() {
        return this.role;
    }

    public CareTeam role(CareTeamRoleEnum role) {
        this.role = role;
        return this;
    }

    public void setRole(CareTeamRoleEnum role) {
        this.role = role;
    }

    public Practitioner getProvider() {
        return this.provider;
    }

    public CareTeam provider(Practitioner practitioner) {
        this.setProvider(practitioner);
        return this;
    }

    public void setProvider(Practitioner practitioner) {
        this.provider = practitioner;
    }

    public PractitionerRole getProviderRole() {
        return this.providerRole;
    }

    public CareTeam providerRole(PractitionerRole practitionerRole) {
        this.setProviderRole(practitionerRole);
        return this;
    }

    public void setProviderRole(PractitionerRole practitionerRole) {
        this.providerRole = practitionerRole;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public CareTeam claim(Claim claim) {
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
        if (!(o instanceof CareTeam)) {
            return false;
        }
        return id != null && id.equals(((CareTeam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CareTeam{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
