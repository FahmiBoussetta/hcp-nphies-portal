package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HumanName.
 */
@Entity
@Table(name = "human_name")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HumanName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "family", nullable = false)
    private String family;

    @OneToMany(mappedBy = "human")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "human" }, allowSetters = true)
    private Set<Givens> givens = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties(value = { "names" }, allowSetters = true)
    private Practitioner practitioner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HumanName id(Long id) {
        this.id = id;
        return this;
    }

    public String getFamily() {
        return this.family;
    }

    public HumanName family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Set<Givens> getGivens() {
        return this.givens;
    }

    public HumanName givens(Set<Givens> givens) {
        this.setGivens(givens);
        return this;
    }

    public HumanName addGiven(Givens givens) {
        this.givens.add(givens);
        givens.setHuman(this);
        return this;
    }

    public HumanName removeGiven(Givens givens) {
        this.givens.remove(givens);
        givens.setHuman(null);
        return this;
    }

    public void setGivens(Set<Givens> givens) {
        if (this.givens != null) {
            this.givens.forEach(i -> i.setHuman(null));
        }
        if (givens != null) {
            givens.forEach(i -> i.setHuman(this));
        }
        this.givens = givens;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public HumanName patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Practitioner getPractitioner() {
        return this.practitioner;
    }

    public HumanName practitioner(Practitioner practitioner) {
        this.setPractitioner(practitioner);
        return this;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HumanName)) {
            return false;
        }
        return id != null && id.equals(((HumanName) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HumanName{" +
            "id=" + getId() +
            ", family='" + getFamily() + "'" +
            "}";
    }
}
