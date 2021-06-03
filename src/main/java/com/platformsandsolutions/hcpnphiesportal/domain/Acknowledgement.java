package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Acknowledgement.
 */
@Entity
@Table(name = "acknowledgement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Acknowledgement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "parsed")
    private String parsed;

    @OneToMany(mappedBy = "acknowledgement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "acknowledgement" }, allowSetters = true)
    private Set<AckErrorMessages> errors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Acknowledgement id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public Acknowledgement value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSystem() {
        return this.system;
    }

    public Acknowledgement system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getParsed() {
        return this.parsed;
    }

    public Acknowledgement parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public Set<AckErrorMessages> getErrors() {
        return this.errors;
    }

    public Acknowledgement errors(Set<AckErrorMessages> ackErrorMessages) {
        this.setErrors(ackErrorMessages);
        return this;
    }

    public Acknowledgement addErrors(AckErrorMessages ackErrorMessages) {
        this.errors.add(ackErrorMessages);
        ackErrorMessages.setAcknowledgement(this);
        return this;
    }

    public Acknowledgement removeErrors(AckErrorMessages ackErrorMessages) {
        this.errors.remove(ackErrorMessages);
        ackErrorMessages.setAcknowledgement(null);
        return this;
    }

    public void setErrors(Set<AckErrorMessages> ackErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setAcknowledgement(null));
        }
        if (ackErrorMessages != null) {
            ackErrorMessages.forEach(i -> i.setAcknowledgement(this));
        }
        this.errors = ackErrorMessages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acknowledgement)) {
            return false;
        }
        return id != null && id.equals(((Acknowledgement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acknowledgement{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", system='" + getSystem() + "'" +
            ", parsed='" + getParsed() + "'" +
            "}";
    }
}
