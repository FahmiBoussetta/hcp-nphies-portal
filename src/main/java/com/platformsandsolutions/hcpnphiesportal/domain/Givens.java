package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Givens.
 */
@Entity
@Table(name = "givens")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Givens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "given", nullable = false)
    private String given;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "text_name")
    private String textName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "givens", "patient", "practitioner" }, allowSetters = true)
    private HumanName human;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Givens id(Long id) {
        this.id = id;
        return this;
    }

    public String getGiven() {
        return this.given;
    }

    public Givens given(String given) {
        this.given = given;
        return this;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Givens prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public Givens suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getTextName() {
        return this.textName;
    }

    public Givens textName(String textName) {
        this.textName = textName;
        return this;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public HumanName getHuman() {
        return this.human;
    }

    public Givens human(HumanName humanName) {
        this.setHuman(humanName);
        return this;
    }

    public void setHuman(HumanName humanName) {
        this.human = humanName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Givens)) {
            return false;
        }
        return id != null && id.equals(((Givens) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Givens{" +
            "id=" + getId() +
            ", given='" + getGiven() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", textName='" + getTextName() + "'" +
            "}";
    }
}
