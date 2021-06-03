package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PayeeTypeEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payee.
 */
@Entity
@Table(name = "payee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PayeeTypeEnum type;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient partyPatient;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization partyOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payee id(Long id) {
        this.id = id;
        return this;
    }

    public PayeeTypeEnum getType() {
        return this.type;
    }

    public Payee type(PayeeTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(PayeeTypeEnum type) {
        this.type = type;
    }

    public Patient getPartyPatient() {
        return this.partyPatient;
    }

    public Payee partyPatient(Patient patient) {
        this.setPartyPatient(patient);
        return this;
    }

    public void setPartyPatient(Patient patient) {
        this.partyPatient = patient;
    }

    public Organization getPartyOrganization() {
        return this.partyOrganization;
    }

    public Payee partyOrganization(Organization organization) {
        this.setPartyOrganization(organization);
        return this;
    }

    public void setPartyOrganization(Organization organization) {
        this.partyOrganization = organization;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payee)) {
            return false;
        }
        return id != null && id.equals(((Payee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payee{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
