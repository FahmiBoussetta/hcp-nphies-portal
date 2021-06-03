package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AdministrativeGenderEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Practitioner.
 */
@Entity
@Table(name = "practitioner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Practitioner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "force_id")
    private String forceId;

    @NotNull
    @Column(name = "practitioner_license", nullable = false)
    private String practitionerLicense;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private AdministrativeGenderEnum gender;

    @OneToMany(mappedBy = "practitioner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "givens", "patient", "practitioner" }, allowSetters = true)
    private Set<HumanName> names = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Practitioner id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Practitioner guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getForceId() {
        return this.forceId;
    }

    public Practitioner forceId(String forceId) {
        this.forceId = forceId;
        return this;
    }

    public void setForceId(String forceId) {
        this.forceId = forceId;
    }

    public String getPractitionerLicense() {
        return this.practitionerLicense;
    }

    public Practitioner practitionerLicense(String practitionerLicense) {
        this.practitionerLicense = practitionerLicense;
        return this;
    }

    public void setPractitionerLicense(String practitionerLicense) {
        this.practitionerLicense = practitionerLicense;
    }

    public AdministrativeGenderEnum getGender() {
        return this.gender;
    }

    public Practitioner gender(AdministrativeGenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(AdministrativeGenderEnum gender) {
        this.gender = gender;
    }

    public Set<HumanName> getNames() {
        return this.names;
    }

    public Practitioner names(Set<HumanName> humanNames) {
        this.setNames(humanNames);
        return this;
    }

    public Practitioner addNames(HumanName humanName) {
        this.names.add(humanName);
        humanName.setPractitioner(this);
        return this;
    }

    public Practitioner removeNames(HumanName humanName) {
        this.names.remove(humanName);
        humanName.setPractitioner(null);
        return this;
    }

    public void setNames(Set<HumanName> humanNames) {
        if (this.names != null) {
            this.names.forEach(i -> i.setPractitioner(null));
        }
        if (humanNames != null) {
            humanNames.forEach(i -> i.setPractitioner(this));
        }
        this.names = humanNames;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Practitioner)) {
            return false;
        }
        return id != null && id.equals(((Practitioner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Practitioner{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", forceId='" + getForceId() + "'" +
            ", practitionerLicense='" + getPractitionerLicense() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
