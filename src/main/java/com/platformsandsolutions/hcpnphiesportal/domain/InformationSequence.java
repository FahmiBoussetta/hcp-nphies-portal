package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InformationSequence.
 */
@Entity
@Table(name = "information_sequence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InformationSequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inf_seq")
    private Integer infSeq;

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

    public InformationSequence id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getInfSeq() {
        return this.infSeq;
    }

    public InformationSequence infSeq(Integer infSeq) {
        this.infSeq = infSeq;
        return this;
    }

    public void setInfSeq(Integer infSeq) {
        this.infSeq = infSeq;
    }

    public Item getItem() {
        return this.item;
    }

    public InformationSequence item(Item item) {
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
        if (!(o instanceof InformationSequence)) {
            return false;
        }
        return id != null && id.equals(((InformationSequence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformationSequence{" +
            "id=" + getId() +
            ", infSeq=" + getInfSeq() +
            "}";
    }
}
