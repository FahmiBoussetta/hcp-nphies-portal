package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CommunicationReasonEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ListCommunicationReasonEnum.
 */
@Entity
@Table(name = "list_communication_reason_enum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ListCommunicationReasonEnum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cr")
    private CommunicationReasonEnum cr;

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

    public ListCommunicationReasonEnum id(Long id) {
        this.id = id;
        return this;
    }

    public CommunicationReasonEnum getCr() {
        return this.cr;
    }

    public ListCommunicationReasonEnum cr(CommunicationReasonEnum cr) {
        this.cr = cr;
        return this;
    }

    public void setCr(CommunicationReasonEnum cr) {
        this.cr = cr;
    }

    public Communication getCommunication() {
        return this.communication;
    }

    public ListCommunicationReasonEnum communication(Communication communication) {
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
        if (!(o instanceof ListCommunicationReasonEnum)) {
            return false;
        }
        return id != null && id.equals(((ListCommunicationReasonEnum) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListCommunicationReasonEnum{" +
            "id=" + getId() +
            ", cr='" + getCr() + "'" +
            "}";
    }
}
