package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetailItem.
 */
@Entity
@Table(name = "detail_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DetailItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "tax", precision = 21, scale = 2)
    private BigDecimal tax;

    @Column(name = "transportation_srca")
    private String transportationSRCA;

    @Column(name = "imaging")
    private String imaging;

    @Column(name = "laboratory")
    private String laboratory;

    @Column(name = "medical_device")
    private String medicalDevice;

    @Column(name = "oral_health_ip")
    private String oralHealthIP;

    @Column(name = "oral_health_op")
    private String oralHealthOP;

    @Column(name = "jhi_procedure")
    private String procedure;

    @Column(name = "services")
    private String services;

    @Column(name = "medication_code")
    private String medicationCode;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    @OneToMany(mappedBy = "detailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    private Set<ReferenceIdentifier> udis = new HashSet<>();

    @OneToMany(mappedBy = "detailItem")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "udis", "detailItem" }, allowSetters = true)
    private Set<SubDetailItem> subDetails = new HashSet<>();

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

    public DetailItem id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public DetailItem sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public DetailItem tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getTransportationSRCA() {
        return this.transportationSRCA;
    }

    public DetailItem transportationSRCA(String transportationSRCA) {
        this.transportationSRCA = transportationSRCA;
        return this;
    }

    public void setTransportationSRCA(String transportationSRCA) {
        this.transportationSRCA = transportationSRCA;
    }

    public String getImaging() {
        return this.imaging;
    }

    public DetailItem imaging(String imaging) {
        this.imaging = imaging;
        return this;
    }

    public void setImaging(String imaging) {
        this.imaging = imaging;
    }

    public String getLaboratory() {
        return this.laboratory;
    }

    public DetailItem laboratory(String laboratory) {
        this.laboratory = laboratory;
        return this;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getMedicalDevice() {
        return this.medicalDevice;
    }

    public DetailItem medicalDevice(String medicalDevice) {
        this.medicalDevice = medicalDevice;
        return this;
    }

    public void setMedicalDevice(String medicalDevice) {
        this.medicalDevice = medicalDevice;
    }

    public String getOralHealthIP() {
        return this.oralHealthIP;
    }

    public DetailItem oralHealthIP(String oralHealthIP) {
        this.oralHealthIP = oralHealthIP;
        return this;
    }

    public void setOralHealthIP(String oralHealthIP) {
        this.oralHealthIP = oralHealthIP;
    }

    public String getOralHealthOP() {
        return this.oralHealthOP;
    }

    public DetailItem oralHealthOP(String oralHealthOP) {
        this.oralHealthOP = oralHealthOP;
        return this;
    }

    public void setOralHealthOP(String oralHealthOP) {
        this.oralHealthOP = oralHealthOP;
    }

    public String getProcedure() {
        return this.procedure;
    }

    public DetailItem procedure(String procedure) {
        this.procedure = procedure;
        return this;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getServices() {
        return this.services;
    }

    public DetailItem services(String services) {
        this.services = services;
        return this;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getMedicationCode() {
        return this.medicationCode;
    }

    public DetailItem medicationCode(String medicationCode) {
        this.medicationCode = medicationCode;
        return this;
    }

    public void setMedicationCode(String medicationCode) {
        this.medicationCode = medicationCode;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public DetailItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return this.unitPrice;
    }

    public DetailItem unitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Set<ReferenceIdentifier> getUdis() {
        return this.udis;
    }

    public DetailItem udis(Set<ReferenceIdentifier> referenceIdentifiers) {
        this.setUdis(referenceIdentifiers);
        return this;
    }

    public DetailItem addUdi(ReferenceIdentifier referenceIdentifier) {
        this.udis.add(referenceIdentifier);
        referenceIdentifier.setDetailItem(this);
        return this;
    }

    public DetailItem removeUdi(ReferenceIdentifier referenceIdentifier) {
        this.udis.remove(referenceIdentifier);
        referenceIdentifier.setDetailItem(null);
        return this;
    }

    public void setUdis(Set<ReferenceIdentifier> referenceIdentifiers) {
        if (this.udis != null) {
            this.udis.forEach(i -> i.setDetailItem(null));
        }
        if (referenceIdentifiers != null) {
            referenceIdentifiers.forEach(i -> i.setDetailItem(this));
        }
        this.udis = referenceIdentifiers;
    }

    public Set<SubDetailItem> getSubDetails() {
        return this.subDetails;
    }

    public DetailItem subDetails(Set<SubDetailItem> subDetailItems) {
        this.setSubDetails(subDetailItems);
        return this;
    }

    public DetailItem addSubDetails(SubDetailItem subDetailItem) {
        this.subDetails.add(subDetailItem);
        subDetailItem.setDetailItem(this);
        return this;
    }

    public DetailItem removeSubDetails(SubDetailItem subDetailItem) {
        this.subDetails.remove(subDetailItem);
        subDetailItem.setDetailItem(null);
        return this;
    }

    public void setSubDetails(Set<SubDetailItem> subDetailItems) {
        if (this.subDetails != null) {
            this.subDetails.forEach(i -> i.setDetailItem(null));
        }
        if (subDetailItems != null) {
            subDetailItems.forEach(i -> i.setDetailItem(this));
        }
        this.subDetails = subDetailItems;
    }

    public Item getItem() {
        return this.item;
    }

    public DetailItem item(Item item) {
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
        if (!(o instanceof DetailItem)) {
            return false;
        }
        return id != null && id.equals(((DetailItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailItem{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            ", tax=" + getTax() +
            ", transportationSRCA='" + getTransportationSRCA() + "'" +
            ", imaging='" + getImaging() + "'" +
            ", laboratory='" + getLaboratory() + "'" +
            ", medicalDevice='" + getMedicalDevice() + "'" +
            ", oralHealthIP='" + getOralHealthIP() + "'" +
            ", oralHealthOP='" + getOralHealthOP() + "'" +
            ", procedure='" + getProcedure() + "'" +
            ", services='" + getServices() + "'" +
            ", medicationCode='" + getMedicationCode() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            "}";
    }
}
