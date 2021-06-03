package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ComErrorMessages.
 */
@Entity
@Table(name = "com_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ComErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "subject", "sender", "recipient", "about", "basedOns", "mediums", "reasonCodes", "payloads", "notes", "errors" },
        allowSetters = true
    )
    private Communication communication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ComErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Communication getCommunication() {
        return this.communication;
    }

    public ComErrorMessages communication(Communication communication) {
        this.setCommunication(communication);
        return this;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((ComErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
