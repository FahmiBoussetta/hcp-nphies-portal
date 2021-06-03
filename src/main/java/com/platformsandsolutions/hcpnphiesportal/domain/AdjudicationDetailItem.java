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
 * A AdjudicationDetailItem.
 */
@Entity
@Table(name = "adjudication_detail_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdjudicationDetailItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @OneToMany(mappedBy = "adjudicationDetailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adjudicationDetailItem" }, allowSetters = true)
    private Set<AdjudicationDetailNotes> notes = new HashSet<>();

    @OneToMany(mappedBy = "adjudicationDetailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adjudicationItem", "adjudicationDetailItem", "adjudicationSubDetailItem" }, allowSetters = true)
    private Set<Adjudication> adjudications = new HashSet<>();

    @OneToMany(mappedBy = "adjudicationDetailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notes", "adjudications", "adjudicationDetailItem" }, allowSetters = true)
    private Set<AdjudicationSubDetailItem> subDetails = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "notes", "adjudications", "details", "claimResponse" }, allowSetters = true)
    private AdjudicationItem adjudicationItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdjudicationDetailItem id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public AdjudicationDetailItem sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<AdjudicationDetailNotes> getNotes() {
        return this.notes;
    }

    public AdjudicationDetailItem notes(Set<AdjudicationDetailNotes> adjudicationDetailNotes) {
        this.setNotes(adjudicationDetailNotes);
        return this;
    }

    public AdjudicationDetailItem addNotes(AdjudicationDetailNotes adjudicationDetailNotes) {
        this.notes.add(adjudicationDetailNotes);
        adjudicationDetailNotes.setAdjudicationDetailItem(this);
        return this;
    }

    public AdjudicationDetailItem removeNotes(AdjudicationDetailNotes adjudicationDetailNotes) {
        this.notes.remove(adjudicationDetailNotes);
        adjudicationDetailNotes.setAdjudicationDetailItem(null);
        return this;
    }

    public void setNotes(Set<AdjudicationDetailNotes> adjudicationDetailNotes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setAdjudicationDetailItem(null));
        }
        if (adjudicationDetailNotes != null) {
            adjudicationDetailNotes.forEach(i -> i.setAdjudicationDetailItem(this));
        }
        this.notes = adjudicationDetailNotes;
    }

    public Set<Adjudication> getAdjudications() {
        return this.adjudications;
    }

    public AdjudicationDetailItem adjudications(Set<Adjudication> adjudications) {
        this.setAdjudications(adjudications);
        return this;
    }

    public AdjudicationDetailItem addAdjudications(Adjudication adjudication) {
        this.adjudications.add(adjudication);
        adjudication.setAdjudicationDetailItem(this);
        return this;
    }

    public AdjudicationDetailItem removeAdjudications(Adjudication adjudication) {
        this.adjudications.remove(adjudication);
        adjudication.setAdjudicationDetailItem(null);
        return this;
    }

    public void setAdjudications(Set<Adjudication> adjudications) {
        if (this.adjudications != null) {
            this.adjudications.forEach(i -> i.setAdjudicationDetailItem(null));
        }
        if (adjudications != null) {
            adjudications.forEach(i -> i.setAdjudicationDetailItem(this));
        }
        this.adjudications = adjudications;
    }

    public Set<AdjudicationSubDetailItem> getSubDetails() {
        return this.subDetails;
    }

    public AdjudicationDetailItem subDetails(Set<AdjudicationSubDetailItem> adjudicationSubDetailItems) {
        this.setSubDetails(adjudicationSubDetailItems);
        return this;
    }

    public AdjudicationDetailItem addSubDetails(AdjudicationSubDetailItem adjudicationSubDetailItem) {
        this.subDetails.add(adjudicationSubDetailItem);
        adjudicationSubDetailItem.setAdjudicationDetailItem(this);
        return this;
    }

    public AdjudicationDetailItem removeSubDetails(AdjudicationSubDetailItem adjudicationSubDetailItem) {
        this.subDetails.remove(adjudicationSubDetailItem);
        adjudicationSubDetailItem.setAdjudicationDetailItem(null);
        return this;
    }

    public void setSubDetails(Set<AdjudicationSubDetailItem> adjudicationSubDetailItems) {
        if (this.subDetails != null) {
            this.subDetails.forEach(i -> i.setAdjudicationDetailItem(null));
        }
        if (adjudicationSubDetailItems != null) {
            adjudicationSubDetailItems.forEach(i -> i.setAdjudicationDetailItem(this));
        }
        this.subDetails = adjudicationSubDetailItems;
    }

    public AdjudicationItem getAdjudicationItem() {
        return this.adjudicationItem;
    }

    public AdjudicationDetailItem adjudicationItem(AdjudicationItem adjudicationItem) {
        this.setAdjudicationItem(adjudicationItem);
        return this;
    }

    public void setAdjudicationItem(AdjudicationItem adjudicationItem) {
        this.adjudicationItem = adjudicationItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdjudicationDetailItem)) {
            return false;
        }
        return id != null && id.equals(((AdjudicationDetailItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjudicationDetailItem{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            "}";
    }
}
