package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OpeOutErrorMessages.
 */
@Entity
@Table(name = "ope_out_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OpeOutErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(value = { "errors" }, allowSetters = true)
    private OperationOutcome operationOutcome;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OpeOutErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public OpeOutErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OperationOutcome getOperationOutcome() {
        return this.operationOutcome;
    }

    public OpeOutErrorMessages operationOutcome(OperationOutcome operationOutcome) {
        this.setOperationOutcome(operationOutcome);
        return this;
    }

    public void setOperationOutcome(OperationOutcome operationOutcome) {
        this.operationOutcome = operationOutcome;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpeOutErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((OpeOutErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpeOutErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
