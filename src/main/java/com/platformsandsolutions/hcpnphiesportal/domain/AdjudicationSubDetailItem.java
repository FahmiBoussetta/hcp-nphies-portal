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
 * A AdjudicationSubDetailItem.
 */
@Entity
@Table(name = "adjudication_sub_detail_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdjudicationSubDetailItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @OneToMany(mappedBy = "adjudicationSubDetailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adjudicationSubDetailItem" }, allowSetters = true)
    private Set<AdjudicationSubDetailNotes> notes = new HashSet<>();

    @OneToMany(mappedBy = "adjudicationSubDetailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "adjudicationItem", "adjudicationDetailItem", "adjudicationSubDetailItem" }, allowSetters = true)
    private Set<Adjudication> adjudications = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "notes", "adjudications", "subDetails", "adjudicationItem" }, allowSetters = true)
    private AdjudicationDetailItem adjudicationDetailItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdjudicationSubDetailItem id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public AdjudicationSubDetailItem sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Set<AdjudicationSubDetailNotes> getNotes() {
        return this.notes;
    }

    public AdjudicationSubDetailItem notes(Set<AdjudicationSubDetailNotes> adjudicationSubDetailNotes) {
        this.setNotes(adjudicationSubDetailNotes);
        return this;
    }

    public AdjudicationSubDetailItem addNotes(AdjudicationSubDetailNotes adjudicationSubDetailNotes) {
        this.notes.add(adjudicationSubDetailNotes);
        adjudicationSubDetailNotes.setAdjudicationSubDetailItem(this);
        return this;
    }

    public AdjudicationSubDetailItem removeNotes(AdjudicationSubDetailNotes adjudicationSubDetailNotes) {
        this.notes.remove(adjudicationSubDetailNotes);
        adjudicationSubDetailNotes.setAdjudicationSubDetailItem(null);
        return this;
    }

    public void setNotes(Set<AdjudicationSubDetailNotes> adjudicationSubDetailNotes) {
        if (this.notes != null) {
            this.notes.forEach(i -> i.setAdjudicationSubDetailItem(null));
        }
        if (adjudicationSubDetailNotes != null) {
            adjudicationSubDetailNotes.forEach(i -> i.setAdjudicationSubDetailItem(this));
        }
        this.notes = adjudicationSubDetailNotes;
    }

    public Set<Adjudication> getAdjudications() {
        return this.adjudications;
    }

    public AdjudicationSubDetailItem adjudications(Set<Adjudication> adjudications) {
        this.setAdjudications(adjudications);
        return this;
    }

    public AdjudicationSubDetailItem addAdjudications(Adjudication adjudication) {
        this.adjudications.add(adjudication);
        adjudication.setAdjudicationSubDetailItem(this);
        return this;
    }

    public AdjudicationSubDetailItem removeAdjudications(Adjudication adjudication) {
        this.adjudications.remove(adjudication);
        adjudication.setAdjudicationSubDetailItem(null);
        return this;
    }

    public void setAdjudications(Set<Adjudication> adjudications) {
        if (this.adjudications != null) {
            this.adjudications.forEach(i -> i.setAdjudicationSubDetailItem(null));
        }
        if (adjudications != null) {
            adjudications.forEach(i -> i.setAdjudicationSubDetailItem(this));
        }
        this.adjudications = adjudications;
    }

    public AdjudicationDetailItem getAdjudicationDetailItem() {
        return this.adjudicationDetailItem;
    }

    public AdjudicationSubDetailItem adjudicationDetailItem(AdjudicationDetailItem adjudicationDetailItem) {
        this.setAdjudicationDetailItem(adjudicationDetailItem);
        return this;
    }

    public void setAdjudicationDetailItem(AdjudicationDetailItem adjudicationDetailItem) {
        this.adjudicationDetailItem = adjudicationDetailItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdjudicationSubDetailItem)) {
            return false;
        }
        return id != null && id.equals(((AdjudicationSubDetailItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjudicationSubDetailItem{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            "}";
    }
}
