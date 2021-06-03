package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReferenceIdentifier.
 */
@Entity
@Table(name = "reference_identifier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReferenceIdentifier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ref")
    private String ref;

    @Column(name = "id_value")
    private String idValue;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "display")
    private String display;

    @ManyToOne
    @JsonIgnoreProperties(value = { "diagnosisSequences", "informationSequences", "udis", "details", "claim" }, allowSetters = true)
    private Item item;

    @ManyToOne
    @JsonIgnoreProperties(value = { "udis", "subDetails", "item" }, allowSetters = true)
    private DetailItem detailItem;

    @ManyToOne
    @JsonIgnoreProperties(value = { "udis", "detailItem" }, allowSetters = true)
    private SubDetailItem subDetailItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReferenceIdentifier id(Long id) {
        this.id = id;
        return this;
    }

    public String getRef() {
        return this.ref;
    }

    public ReferenceIdentifier ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getIdValue() {
        return this.idValue;
    }

    public ReferenceIdentifier idValue(String idValue) {
        this.idValue = idValue;
        return this;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public ReferenceIdentifier identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDisplay() {
        return this.display;
    }

    public ReferenceIdentifier display(String display) {
        this.display = display;
        return this;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Item getItem() {
        return this.item;
    }

    public ReferenceIdentifier item(Item item) {
        this.setItem(item);
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public DetailItem getDetailItem() {
        return this.detailItem;
    }

    public ReferenceIdentifier detailItem(DetailItem detailItem) {
        this.setDetailItem(detailItem);
        return this;
    }

    public void setDetailItem(DetailItem detailItem) {
        this.detailItem = detailItem;
    }

    public SubDetailItem getSubDetailItem() {
        return this.subDetailItem;
    }

    public ReferenceIdentifier subDetailItem(SubDetailItem subDetailItem) {
        this.setSubDetailItem(subDetailItem);
        return this;
    }

    public void setSubDetailItem(SubDetailItem subDetailItem) {
        this.subDetailItem = subDetailItem;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferenceIdentifier)) {
            return false;
        }
        return id != null && id.equals(((ReferenceIdentifier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferenceIdentifier{" +
            "id=" + getId() +
            ", ref='" + getRef() + "'" +
            ", idValue='" + getIdValue() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", display='" + getDisplay() + "'" +
            "}";
    }
}
