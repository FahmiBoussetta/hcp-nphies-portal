package com.platformsandsolutions.hcpnphiesportal.domain;

import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AccidentTypeEnum;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Accident.
 */
@Entity
@Table(name = "accident")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Accident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccidentTypeEnum type;

    @OneToOne
    @JoinColumn(unique = true)
    private Address location;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accident id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDate() {
        return this.date;
    }

    public Accident date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public AccidentTypeEnum getType() {
        return this.type;
    }

    public Accident type(AccidentTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(AccidentTypeEnum type) {
        this.type = type;
    }

    public Address getLocation() {
        return this.location;
    }

    public Accident location(Address address) {
        this.setLocation(address);
        return this;
    }

    public void setLocation(Address address) {
        this.location = address;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accident)) {
            return false;
        }
        return id != null && id.equals(((Accident) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accident{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
