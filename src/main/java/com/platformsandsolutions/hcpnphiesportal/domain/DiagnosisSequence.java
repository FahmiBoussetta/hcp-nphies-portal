package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DiagnosisSequence.
 */
@Entity
@Table(name = "diagnosis_sequence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiagnosisSequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diag_seq")
    private Integer diagSeq;

    @ManyToOne
    @JsonIgnoreProperties(value = { "diagnosisSequences", "informationSequences", "udis", "details", "claim" }, allowSetters = true)
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiagnosisSequence id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getDiagSeq() {
        return this.diagSeq;
    }

    public DiagnosisSequence diagSeq(Integer diagSeq) {
        this.diagSeq = diagSeq;
        return this;
    }

    public void setDiagSeq(Integer diagSeq) {
        this.diagSeq = diagSeq;
    }

    public Item getItem() {
        return this.item;
    }

    public DiagnosisSequence item(Item item) {
        this.setItem(item);
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiagnosisSequence)) {
            return false;
        }
        return id != null && id.equals(((DiagnosisSequence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiagnosisSequence{" +
            "id=" + getId() +
            ", diagSeq=" + getDiagSeq() +
            "}";
    }
}
