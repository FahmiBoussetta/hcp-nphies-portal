package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ExemptionTypeEnum;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ExemptionComponent.
 */
@Entity
@Table(name = "exemption_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExemptionComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExemptionTypeEnum type;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

    @ManyToOne
    @JsonIgnoreProperties(value = { "exceptions", "coverage" }, allowSetters = true)
    private CostToBeneficiaryComponent costToBeneficiary;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExemptionComponent id(Long id) {
        this.id = id;
        return this;
    }

    public ExemptionTypeEnum getType() {
        return this.type;
    }

    public ExemptionComponent type(ExemptionTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(ExemptionTypeEnum type) {
        this.type = type;
    }

    public Instant getStart() {
        return this.start;
    }

    public ExemptionComponent start(Instant start) {
        this.start = start;
        return this;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return this.end;
    }

    public ExemptionComponent end(Instant end) {
        this.end = end;
        return this;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public CostToBeneficiaryComponent getCostToBeneficiary() {
        return this.costToBeneficiary;
    }

    public ExemptionComponent costToBeneficiary(CostToBeneficiaryComponent costToBeneficiaryComponent) {
        this.setCostToBeneficiary(costToBeneficiaryComponent);
        return this;
    }

    public void setCostToBeneficiary(CostToBeneficiaryComponent costToBeneficiaryComponent) {
        this.costToBeneficiary = costToBeneficiaryComponent;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExemptionComponent)) {
            return false;
        }
        return id != null && id.equals(((ExemptionComponent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExemptionComponent{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
