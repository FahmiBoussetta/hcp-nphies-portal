package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SpecialtyEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ListSpecialtyEnum.
 */
@Entity
@Table(name = "list_specialty_enum")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ListSpecialtyEnum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "s")
    private SpecialtyEnum s;

    @ManyToOne
    @JsonIgnoreProperties(value = { "practitioner", "organization", "codes", "specialties" }, allowSetters = true)
    private PractitionerRole practitionerRole;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListSpecialtyEnum id(Long id) {
        this.id = id;
        return this;
    }

    public SpecialtyEnum getS() {
        return this.s;
    }

    public ListSpecialtyEnum s(SpecialtyEnum s) {
        this.s = s;
        return this;
    }

    public void setS(SpecialtyEnum s) {
        this.s = s;
    }

    public PractitionerRole getPractitionerRole() {
        return this.practitionerRole;
    }

    public ListSpecialtyEnum practitionerRole(PractitionerRole practitionerRole) {
        this.setPractitionerRole(practitionerRole);
        return this;
    }

    public void setPractitionerRole(PractitionerRole practitionerRole) {
        this.practitionerRole = practitionerRole;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListSpecialtyEnum)) {
            return false;
        }
        return id != null && id.equals(((ListSpecialtyEnum) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListSpecialtyEnum{" +
            "id=" + getId() +
            ", s='" + getS() + "'" +
            "}";
    }
}
