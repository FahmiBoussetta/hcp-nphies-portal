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
 * A AdjudicationItem.
 */
@Entity
@Table(name = "adjudication_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdjudicationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outcome")
    private String outcome;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @OneToMany(mappedBy = "adjudicationItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adjudicationItem" }, allowSetters = true)
    private Set<AdjudicationNotes> notes = new HashSet<>();

    @OneToMany(mappedBy = "adjudicationItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adjudicationItem", "adjudicationDetailItem", "adjudicationSubDetailItem" }, allowSetters = true)
    private Set<Adjudication> adjudications = new HashSet<>();

    @OneToMany(mappedBy = "adjudicationItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notes", "adjudications", "subDetails", "adjudicationItem" }, allowSetters = true)
    private Set<AdjudicationDetailItem> details = new HashSet<>();

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

    public AdjudicationItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getOutcome() {
        return this.outcome;
    }

    public AdjudicationItem outcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public AdjudicationItem sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<AdjudicationNotes> getNotes() {
        return this.notes;
    }

    public AdjudicationItem notes(Set<AdjudicationNotes> adjudicationNotes) {
        this.setNotes(adjudicationNotes);
        return this;
    }

    public AdjudicationItem addNotes(AdjudicationNotes adjudicationNotes) {
        this.notes.add(adjudicationNotes);
        adjudicationNotes.setAdjudicationItem(this);
        return this;
    }

    public AdjudicationItem removeNotes(AdjudicationNotes adjudicationNotes) {
        this.notes.remove(adjudicationNotes);
        adjudicationNotes.setAdjudicationItem(null);
        return this;
    }

    public void setNotes(Set<AdjudicationNotes> adjudicationNotes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setAdjudicationItem(null));
        }
        if (adjudicationNotes != null) {
            adjudicationNotes.forEach(i -> i.setAdjudicationItem(this));
        }
        this.notes = adjudicationNotes;
    }

    public Set<Adjudication> getAdjudications() {
        return this.adjudications;
    }

    public AdjudicationItem adjudications(Set<Adjudication> adjudications) {
        this.setAdjudications(adjudications);
        return this;
    }

    public AdjudicationItem addAdjudications(Adjudication adjudication) {
        this.adjudications.add(adjudication);
        adjudication.setAdjudicationItem(this);
        return this;
    }

    public AdjudicationItem removeAdjudications(Adjudication adjudication) {
        this.adjudications.remove(adjudication);
        adjudication.setAdjudicationItem(null);
        return this;
    }

    public void setAdjudications(Set<Adjudication> adjudications) {
        if (this.adjudications != null) {
            this.adjudications.forEach(i -> i.setAdjudicationItem(null));
        }
        if (adjudications != null) {
            adjudications.forEach(i -> i.setAdjudicationItem(this));
        }
        this.adjudications = adjudications;
    }

    public Set<AdjudicationDetailItem> getDetails() {
        return this.details;
    }

    public AdjudicationItem details(Set<AdjudicationDetailItem> adjudicationDetailItems) {
        this.setDetails(adjudicationDetailItems);
        return this;
    }

    public AdjudicationItem addDetails(AdjudicationDetailItem adjudicationDetailItem) {
        this.details.add(adjudicationDetailItem);
        adjudicationDetailItem.setAdjudicationItem(this);
        return this;
    }

    public AdjudicationItem removeDetails(AdjudicationDetailItem adjudicationDetailItem) {
        this.details.remove(adjudicationDetailItem);
        adjudicationDetailItem.setAdjudicationItem(null);
        return this;
    }

    public void setDetails(Set<AdjudicationDetailItem> adjudicationDetailItems) {
        if (this.details != null) {
            this.details.forEach(i -> i.setAdjudicationItem(null));
        }
        if (adjudicationDetailItems != null) {
            adjudicationDetailItems.forEach(i -> i.setAdjudicationItem(this));
        }
        this.details = adjudicationDetailItems;
    }

    public ClaimResponse getClaimResponse() {
        return this.claimResponse;
    }

    public AdjudicationItem claimResponse(ClaimResponse claimResponse) {
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
        if (!(o instanceof AdjudicationItem)) {
            return false;
        }
        return id != null && id.equals(((AdjudicationItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjudicationItem{" +
            "id=" + getId() +
            ", outcome='" + getOutcome() + "'" +
            ", sequence=" + getSequence() +
            "}";
    }
}
