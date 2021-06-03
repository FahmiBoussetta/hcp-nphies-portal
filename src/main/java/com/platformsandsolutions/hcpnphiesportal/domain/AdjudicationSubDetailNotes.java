package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdjudicationSubDetailNotes.
 */
@Entity
@Table(name = "adjudication_sub_detail_notes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdjudicationSubDetailNotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = { "notes", "adjudications", "adjudicationDetailItem" }, allowSetters = true)
    private AdjudicationSubDetailItem adjudicationSubDetailItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdjudicationSubDetailNotes id(Long id) {
        this.id = id;
        return this;
    }

    public String getNote() {
        return this.note;
    }

    public AdjudicationSubDetailNotes note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AdjudicationSubDetailItem getAdjudicationSubDetailItem() {
        return this.adjudicationSubDetailItem;
    }

    public AdjudicationSubDetailNotes adjudicationSubDetailItem(AdjudicationSubDetailItem adjudicationSubDetailItem) {
        this.setAdjudicationSubDetailItem(adjudicationSubDetailItem);
        return this;
    }

    public void setAdjudicationSubDetailItem(AdjudicationSubDetailItem adjudicationSubDetailItem) {
        this.adjudicationSubDetailItem = adjudicationSubDetailItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdjudicationSubDetailNotes)) {
            return false;
        }
        return id != null && id.equals(((AdjudicationSubDetailNotes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjudicationSubDetailNotes{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
