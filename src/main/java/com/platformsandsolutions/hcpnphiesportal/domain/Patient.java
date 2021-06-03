package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AdministrativeGenderEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.MaritalStatusEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ReligionEnum;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "force_id")
    private String forceId;

    @Column(name = "resident_number")
    private String residentNumber;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "national_health_id")
    private String nationalHealthId;

    @Column(name = "iqama")
    private String iqama;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private ReligionEnum religion;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private AdministrativeGenderEnum gender;

    @Column(name = "birth_date")
    private Instant birthDate;

    @Column(name = "deceased_date")
    private Instant deceasedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatusEnum maritalStatus;

    @JsonIgnoreProperties(value = { "name", "organization" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Contact contacts;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "patient")
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

    public Patient id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Patient guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getForceId() {
        return this.forceId;
    }

    public Patient forceId(String forceId) {
        this.forceId = forceId;
        return this;
    }

    public void setForceId(String forceId) {
        this.forceId = forceId;
    }

    public String getResidentNumber() {
        return this.residentNumber;
    }

    public Patient residentNumber(String residentNumber) {
        this.residentNumber = residentNumber;
        return this;
    }

    public void setResidentNumber(String residentNumber) {
        this.residentNumber = residentNumber;
    }

    public String getPassportNumber() {
        return this.passportNumber;
    }

    public Patient passportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationalHealthId() {
        return this.nationalHealthId;
    }

    public Patient nationalHealthId(String nationalHealthId) {
        this.nationalHealthId = nationalHealthId;
        return this;
    }

    public void setNationalHealthId(String nationalHealthId) {
        this.nationalHealthId = nationalHealthId;
    }

    public String getIqama() {
        return this.iqama;
    }

    public Patient iqama(String iqama) {
        this.iqama = iqama;
        return this;
    }

    public void setIqama(String iqama) {
        this.iqama = iqama;
    }

    public ReligionEnum getReligion() {
        return this.religion;
    }

    public Patient religion(ReligionEnum religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(ReligionEnum religion) {
        this.religion = religion;
    }

    public AdministrativeGenderEnum getGender() {
        return this.gender;
    }

    public Patient gender(AdministrativeGenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(AdministrativeGenderEnum gender) {
        this.gender = gender;
    }

    public Instant getBirthDate() {
        return this.birthDate;
    }

    public Patient birthDate(Instant birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Instant getDeceasedDate() {
        return this.deceasedDate;
    }

    public Patient deceasedDate(Instant deceasedDate) {
        this.deceasedDate = deceasedDate;
        return this;
    }

    public void setDeceasedDate(Instant deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    public MaritalStatusEnum getMaritalStatus() {
        return this.maritalStatus;
    }

    public Patient maritalStatus(MaritalStatusEnum maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatusEnum maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Contact getContacts() {
        return this.contacts;
    }

    public Patient contacts(Contact contact) {
        this.setContacts(contact);
        return this;
    }

    public void setContacts(Contact contact) {
        this.contacts = contact;
    }

    public Address getAddress() {
        return this.address;
    }

    public Patient address(Address address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<HumanName> getNames() {
        return this.names;
    }

    public Patient names(Set<HumanName> humanNames) {
        this.setNames(humanNames);
        return this;
    }

    public Patient addNames(HumanName humanName) {
        this.names.add(humanName);
        humanName.setPatient(this);
        return this;
    }

    public Patient removeNames(HumanName humanName) {
        this.names.remove(humanName);
        humanName.setPatient(null);
        return this;
    }

    public void setNames(Set<HumanName> humanNames) {
        if (this.names != null) {
            this.names.forEach(i -> i.setPatient(null));
        }
        if (humanNames != null) {
            humanNames.forEach(i -> i.setPatient(this));
        }
        this.names = humanNames;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", forceId='" + getForceId() + "'" +
            ", residentNumber='" + getResidentNumber() + "'" +
            ", passportNumber='" + getPassportNumber() + "'" +
            ", nationalHealthId='" + getNationalHealthId() + "'" +
            ", iqama='" + getIqama() + "'" +
            ", religion='" + getReligion() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", deceasedDate='" + getDeceasedDate() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            "}";
    }
}
