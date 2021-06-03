package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CRErrorMessages.
 */
@Entity
@Table(name = "cr_error_messages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CRErrorMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JsonIgnoreProperties(value = { "errors", "items", "totals" }, allowSetters = true)
    private ClaimResponse claimResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CRErrorMessages id(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public CRErrorMessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ClaimResponse getClaimResponse() {
        return this.claimResponse;
    }

    public CRErrorMessages claimResponse(ClaimResponse claimResponse) {
        this.setClaimResponse(claimResponse);
        return this;
    }

    public void setClaimResponse(ClaimResponse claimResponse) {
        this.claimResponse = claimResponse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CRErrorMessages)) {
            return false;
        }
        return id != null && id.equals(((CRErrorMessages) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CRErrorMessages{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
