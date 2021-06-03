package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdjudicationNotes.
 */
@Entity
@Table(name = "adjudication_notes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdjudicationNotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

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

    public AdjudicationNotes id(Long id) {
        this.id = id;
        return this;
    }

    public String getNote() {
        return this.note;
    }

    public AdjudicationNotes note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AdjudicationItem getAdjudicationItem() {
        return this.adjudicationItem;
    }

    public AdjudicationNotes adjudicationItem(AdjudicationItem adjudicationItem) {
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
        if (!(o instanceof AdjudicationNotes)) {
            return false;
        }
        return id != null && id.equals(((AdjudicationNotes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjudicationNotes{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
