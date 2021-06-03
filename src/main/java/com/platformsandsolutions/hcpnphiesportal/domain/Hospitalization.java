package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AdmitSourceEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.DischargeDispositionEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ReAdmissionEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hospitalization.
 */
@Entity
@Table(name = "hospitalization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hospitalization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "admit_source")
    private AdmitSourceEnum admitSource;

    @Enumerated(EnumType.STRING)
    @Column(name = "re_admission")
    private ReAdmissionEnum reAdmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "discharge_disposition")
    private DischargeDispositionEnum dischargeDisposition;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization origin;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospitalization id(Long id) {
        this.id = id;
        return this;
    }

    public AdmitSourceEnum getAdmitSource() {
        return this.admitSource;
    }

    public Hospitalization admitSource(AdmitSourceEnum admitSource) {
        this.admitSource = admitSource;
        return this;
    }

    public void setAdmitSource(AdmitSourceEnum admitSource) {
        this.admitSource = admitSource;
    }

    public ReAdmissionEnum getReAdmission() {
        return this.reAdmission;
    }

    public Hospitalization reAdmission(ReAdmissionEnum reAdmission) {
        this.reAdmission = reAdmission;
        return this;
    }

    public void setReAdmission(ReAdmissionEnum reAdmission) {
        this.reAdmission = reAdmission;
    }

    public DischargeDispositionEnum getDischargeDisposition() {
        return this.dischargeDisposition;
    }

    public Hospitalization dischargeDisposition(DischargeDispositionEnum dischargeDisposition) {
        this.dischargeDisposition = dischargeDisposition;
        return this;
    }

    public void setDischargeDisposition(DischargeDispositionEnum dischargeDisposition) {
        this.dischargeDisposition = dischargeDisposition;
    }

    public Organization getOrigin() {
        return this.origin;
    }

    public Hospitalization origin(Organization organization) {
        this.setOrigin(organization);
        return this;
    }

    public void setOrigin(Organization organization) {
        this.origin = organization;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospitalization)) {
            return false;
        }
        return id != null && id.equals(((Hospitalization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hospitalization{" +
            "id=" + getId() +
            ", admitSource='" + getAdmitSource() + "'" +
            ", reAdmission='" + getReAdmission() + "'" +
            ", dischargeDisposition='" + getDischargeDisposition() + "'" +
            "}";
    }
}
