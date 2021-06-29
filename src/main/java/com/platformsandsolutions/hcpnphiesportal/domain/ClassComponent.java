package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClassTypeEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClassComponent.
 */
@Entity
@Table(name = "class_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClassComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ClassTypeEnum type;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "subscriberPatient", "beneficiary", "payor", "classComponents", "costToBeneficiaryComponents", "coverageEligibilityRequests",
        },
        allowSetters = true
    )
    private Coverage coverage;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassComponent id(Long id) {
        this.id = id;
        return this;
    }

    public ClassTypeEnum getType() {
        return this.type;
    }

    public ClassComponent type(ClassTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(ClassTypeEnum type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public ClassComponent value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public ClassComponent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coverage getCoverage() {
        return this.coverage;
    }

    public ClassComponent coverage(Coverage coverage) {
        this.setCoverage(coverage);
        return this;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassComponent)) {
            return false;
        }
        return id != null && id.equals(((ClassComponent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassComponent{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
