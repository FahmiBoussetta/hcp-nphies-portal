package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClaimResponse.
 */
@Entity
@Table(name = "claim_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClaimResponse implements Serializable {

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

    @Column(name = "outcome")
    private String outcome;

    @OneToMany(mappedBy = "claimResponse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "claimResponse" }, allowSetters = true)
    private Set<CRErrorMessages> errors = new HashSet<>();

    @OneToMany(mappedBy = "claimResponse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notes", "adjudications", "details", "claimResponse" }, allowSetters = true)
    private Set<AdjudicationItem> items = new HashSet<>();

    @OneToMany(mappedBy = "claimResponse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "claimResponse" }, allowSetters = true)
    private Set<Total> totals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClaimResponse id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public ClaimResponse value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSystem() {
        return this.system;
    }

    public ClaimResponse system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getParsed() {
        return this.parsed;
    }

    public ClaimResponse parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getOutcome() {
        return this.outcome;
    }

    public ClaimResponse outcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Set<CRErrorMessages> getErrors() {
        return this.errors;
    }

    public ClaimResponse errors(Set<CRErrorMessages> cRErrorMessages) {
        this.setErrors(cRErrorMessages);
        return this;
    }

    public ClaimResponse addErrors(CRErrorMessages cRErrorMessages) {
        this.errors.add(cRErrorMessages);
        cRErrorMessages.setClaimResponse(this);
        return this;
    }

    public ClaimResponse removeErrors(CRErrorMessages cRErrorMessages) {
        this.errors.remove(cRErrorMessages);
        cRErrorMessages.setClaimResponse(null);
        return this;
    }

    public void setErrors(Set<CRErrorMessages> cRErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setClaimResponse(null));
        }
        if (cRErrorMessages != null) {
            cRErrorMessages.forEach(i -> i.setClaimResponse(this));
        }
        this.errors = cRErrorMessages;
    }

    public Set<AdjudicationItem> getItems() {
        return this.items;
    }

    public ClaimResponse items(Set<AdjudicationItem> adjudicationItems) {
        this.setItems(adjudicationItems);
        return this;
    }

    public ClaimResponse addItems(AdjudicationItem adjudicationItem) {
        this.items.add(adjudicationItem);
        adjudicationItem.setClaimResponse(this);
        return this;
    }

    public ClaimResponse removeItems(AdjudicationItem adjudicationItem) {
        this.items.remove(adjudicationItem);
        adjudicationItem.setClaimResponse(null);
        return this;
    }

    public void setItems(Set<AdjudicationItem> adjudicationItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setClaimResponse(null));
        }
        if (adjudicationItems != null) {
            adjudicationItems.forEach(i -> i.setClaimResponse(this));
        }
        this.items = adjudicationItems;
    }

    public Set<Total> getTotals() {
        return this.totals;
    }

    public ClaimResponse totals(Set<Total> totals) {
        this.setTotals(totals);
        return this;
    }

    public ClaimResponse addTotal(Total total) {
        this.totals.add(total);
        total.setClaimResponse(this);
        return this;
    }

    public ClaimResponse removeTotal(Total total) {
        this.totals.remove(total);
        total.setClaimResponse(null);
        return this;
    }

    public void setTotals(Set<Total> totals) {
        if (this.totals != null) {
            this.totals.forEach(i -> i.setClaimResponse(null));
        }
        if (totals != null) {
            totals.forEach(i -> i.setClaimResponse(this));
        }
        this.totals = totals;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClaimResponse)) {
            return false;
        }
        return id != null && id.equals(((ClaimResponse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClaimResponse{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", system='" + getSystem() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", outcome='" + getOutcome() + "'" +
            "}";
    }
}
