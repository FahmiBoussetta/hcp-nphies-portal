package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AckErrorMessages.
 */
@Entity
@Table(name = "ack_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AckErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(value = { "errors" }, allowSetters = true)
    private Acknowledgement acknowledgement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AckErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public AckErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Acknowledgement getAcknowledgement() {
        return this.acknowledgement;
    }

    public AckErrorMessages acknowledgement(Acknowledgement acknowledgement) {
        this.setAcknowledgement(acknowledgement);
        return this;
    }

    public void setAcknowledgement(Acknowledgement acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AckErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((AckErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AckErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
