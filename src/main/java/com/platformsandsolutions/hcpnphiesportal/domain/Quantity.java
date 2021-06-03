package com.platformsandsolutions.hcpnphiesportal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Quantity.
 */
@Entity
@Table(name = "quantity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", precision = 21, scale = 2)
    private BigDecimal value;

    @Column(name = "unit")
    private String unit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quantity id(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public Quantity value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUnit() {
        return this.unit;
    }

    public Quantity unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quantity)) {
            return false;
        }
        return id != null && id.equals(((Quantity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quantity{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
