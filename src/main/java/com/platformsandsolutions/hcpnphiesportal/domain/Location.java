package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.LocationTypeEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "identifier")
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LocationTypeEnum type;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization managingOrganization;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Location guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Location identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocationTypeEnum getType() {
        return this.type;
    }

    public Location type(LocationTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(LocationTypeEnum type) {
        this.type = type;
    }

    public Organization getManagingOrganization() {
        return this.managingOrganization;
    }

    public Location managingOrganization(Organization organization) {
        this.setManagingOrganization(organization);
        return this;
    }

    public void setManagingOrganization(Organization organization) {
        this.managingOrganization = organization;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
