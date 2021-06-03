package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payload.
 */
@Entity
@Table(name = "payload")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Payload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_string")
    private String contentString;

    @OneToOne
    @JoinColumn(unique = true)
    private Attachment contentAttachment;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier contentReference;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "subject", "sender", "recipient", "about", "basedOns", "mediums", "reasonCodes", "payloads", "notes", "errors" },
        allowSetters = true
    )
    private Communication communication;

    @ManyToOne
    @JsonIgnoreProperties(value = { "subject", "about", "sender", "payloads", "notes", "communication" }, allowSetters = true)
    private CommunicationRequest communicationRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payload id(Long id) {
        this.id = id;
        return this;
    }

    public String getContentString() {
        return this.contentString;
    }

    public Payload contentString(String contentString) {
        this.contentString = contentString;
        return this;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public Attachment getContentAttachment() {
        return this.contentAttachment;
    }

    public Payload contentAttachment(Attachment attachment) {
        this.setContentAttachment(attachment);
        return this;
    }

    public void setContentAttachment(Attachment attachment) {
        this.contentAttachment = attachment;
    }

    public ReferenceIdentifier getContentReference() {
        return this.contentReference;
    }

    public Payload contentReference(ReferenceIdentifier referenceIdentifier) {
        this.setContentReference(referenceIdentifier);
        return this;
    }

    public void setContentReference(ReferenceIdentifier referenceIdentifier) {
        this.contentReference = referenceIdentifier;
    }

    public Communication getCommunication() {
        return this.communication;
    }

    public Payload communication(Communication communication) {
        this.setCommunication(communication);
        return this;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public CommunicationRequest getCommunicationRequest() {
        return this.communicationRequest;
    }

    public Payload communicationRequest(CommunicationRequest communicationRequest) {
        this.setCommunicationRequest(communicationRequest);
        return this;
    }

    public void setCommunicationRequest(CommunicationRequest communicationRequest) {
        this.communicationRequest = communicationRequest;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payload)) {
            return false;
        }
        return id != null && id.equals(((Payload) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payload{" +
            "id=" + getId() +
            ", contentString='" + getContentString() + "'" +
            "}";
    }
}
