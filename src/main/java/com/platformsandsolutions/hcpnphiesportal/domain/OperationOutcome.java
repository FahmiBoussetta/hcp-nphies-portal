package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OperationOutcome.
 */
@Entity
@Table(name = "operation_outcome")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperationOutcome implements Serializable {

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

    @OneToMany(mappedBy = "operationOutcome")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "operationOutcome" }, allowSetters = true)
    private Set<OpeOutErrorMessages> errors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationOutcome id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public OperationOutcome value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSystem() {
        return this.system;
    }

    public OperationOutcome system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getParsed() {
        return this.parsed;
    }

    public OperationOutcome parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public Set<OpeOutErrorMessages> getErrors() {
        return this.errors;
    }

    public OperationOutcome errors(Set<OpeOutErrorMessages> opeOutErrorMessages) {
        this.setErrors(opeOutErrorMessages);
        return this;
    }

    public OperationOutcome addErrors(OpeOutErrorMessages opeOutErrorMessages) {
        this.errors.add(opeOutErrorMessages);
        opeOutErrorMessages.setOperationOutcome(this);
        return this;
    }

    public OperationOutcome removeErrors(OpeOutErrorMessages opeOutErrorMessages) {
        this.errors.remove(opeOutErrorMessages);
        opeOutErrorMessages.setOperationOutcome(null);
        return this;
    }

    public void setErrors(Set<OpeOutErrorMessages> opeOutErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setOperationOutcome(null));
        }
        if (opeOutErrorMessages != null) {
            opeOutErrorMessages.forEach(i -> i.setOperationOutcome(this));
        }
        this.errors = opeOutErrorMessages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationOutcome)) {
            return false;
        }
        return id != null && id.equals(((OperationOutcome) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationOutcome{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", system='" + getSystem() + "'" +
            ", parsed='" + getParsed() + "'" +
            "}";
    }
}
