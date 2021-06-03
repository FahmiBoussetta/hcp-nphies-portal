package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoCategoryEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoCodeFdiEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoCodeVisitEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoReasonEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoReasonMissingToothEnum;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SupportingInfo.
 */
@Entity
@Table(name = "supporting_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupportingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "code_loinc")
    private String codeLOINC;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private SupportingInfoCategoryEnum category;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_visit")
    private SupportingInfoCodeVisitEnum codeVisit;

    @Enumerated(EnumType.STRING)
    @Column(name = "code_fdi_oral")
    private SupportingInfoCodeFdiEnum codeFdiOral;

    @Column(name = "timing")
    private Instant timing;

    @Column(name = "timing_end")
    private Instant timingEnd;

    @Column(name = "value_boolean")
    private Boolean valueBoolean;

    @Column(name = "value_string")
    private String valueString;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason")
    private SupportingInfoReasonEnum reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason_missing_tooth")
    private SupportingInfoReasonMissingToothEnum reasonMissingTooth;

    @OneToOne
    @JoinColumn(unique = true)
    private Quantity valueQuantity;

    @OneToOne
    @JoinColumn(unique = true)
    private Attachment valueAttachment;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier valueReference;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "encounter",
            "eligibilityResponse",
            "patient",
            "provider",
            "insurer",
            "prescription",
            "originalPrescription",
            "payee",
            "referral",
            "facility",
            "accident",
            "diagnoses",
            "insurances",
            "items",
            "errors",
            "relateds",
            "careTeams",
            "supportingInfos",
        },
        allowSetters = true
    )
    private Claim claim;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupportingInfo id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequence() {
        return this.sequence;
    }

    public SupportingInfo sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getCodeLOINC() {
        return this.codeLOINC;
    }

    public SupportingInfo codeLOINC(String codeLOINC) {
        this.codeLOINC = codeLOINC;
        return this;
    }

    public void setCodeLOINC(String codeLOINC) {
        this.codeLOINC = codeLOINC;
    }

    public SupportingInfoCategoryEnum getCategory() {
        return this.category;
    }

    public SupportingInfo category(SupportingInfoCategoryEnum category) {
        this.category = category;
        return this;
    }

    public void setCategory(SupportingInfoCategoryEnum category) {
        this.category = category;
    }

    public SupportingInfoCodeVisitEnum getCodeVisit() {
        return this.codeVisit;
    }

    public SupportingInfo codeVisit(SupportingInfoCodeVisitEnum codeVisit) {
        this.codeVisit = codeVisit;
        return this;
    }

    public void setCodeVisit(SupportingInfoCodeVisitEnum codeVisit) {
        this.codeVisit = codeVisit;
    }

    public SupportingInfoCodeFdiEnum getCodeFdiOral() {
        return this.codeFdiOral;
    }

    public SupportingInfo codeFdiOral(SupportingInfoCodeFdiEnum codeFdiOral) {
        this.codeFdiOral = codeFdiOral;
        return this;
    }

    public void setCodeFdiOral(SupportingInfoCodeFdiEnum codeFdiOral) {
        this.codeFdiOral = codeFdiOral;
    }

    public Instant getTiming() {
        return this.timing;
    }

    public SupportingInfo timing(Instant timing) {
        this.timing = timing;
        return this;
    }

    public void setTiming(Instant timing) {
        this.timing = timing;
    }

    public Instant getTimingEnd() {
        return this.timingEnd;
    }

    public SupportingInfo timingEnd(Instant timingEnd) {
        this.timingEnd = timingEnd;
        return this;
    }

    public void setTimingEnd(Instant timingEnd) {
        this.timingEnd = timingEnd;
    }

    public Boolean getValueBoolean() {
        return this.valueBoolean;
    }

    public SupportingInfo valueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
        return this;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public String getValueString() {
        return this.valueString;
    }

    public SupportingInfo valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public SupportingInfoReasonEnum getReason() {
        return this.reason;
    }

    public SupportingInfo reason(SupportingInfoReasonEnum reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(SupportingInfoReasonEnum reason) {
        this.reason = reason;
    }

    public SupportingInfoReasonMissingToothEnum getReasonMissingTooth() {
        return this.reasonMissingTooth;
    }

    public SupportingInfo reasonMissingTooth(SupportingInfoReasonMissingToothEnum reasonMissingTooth) {
        this.reasonMissingTooth = reasonMissingTooth;
        return this;
    }

    public void setReasonMissingTooth(SupportingInfoReasonMissingToothEnum reasonMissingTooth) {
        this.reasonMissingTooth = reasonMissingTooth;
    }

    public Quantity getValueQuantity() {
        return this.valueQuantity;
    }

    public SupportingInfo valueQuantity(Quantity quantity) {
        this.setValueQuantity(quantity);
        return this;
    }

    public void setValueQuantity(Quantity quantity) {
        this.valueQuantity = quantity;
    }

    public Attachment getValueAttachment() {
        return this.valueAttachment;
    }

    public SupportingInfo valueAttachment(Attachment attachment) {
        this.setValueAttachment(attachment);
        return this;
    }

    public void setValueAttachment(Attachment attachment) {
        this.valueAttachment = attachment;
    }

    public ReferenceIdentifier getValueReference() {
        return this.valueReference;
    }

    public SupportingInfo valueReference(ReferenceIdentifier referenceIdentifier) {
        this.setValueReference(referenceIdentifier);
        return this;
    }

    public void setValueReference(ReferenceIdentifier referenceIdentifier) {
        this.valueReference = referenceIdentifier;
    }

    public Claim getClaim() {
        return this.claim;
    }

    public SupportingInfo claim(Claim claim) {
        this.setClaim(claim);
        return this;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupportingInfo)) {
            return false;
        }
        return id != null && id.equals(((SupportingInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupportingInfo{" +
            "id=" + getId() +
            ", sequence=" + getSequence() +
            ", codeLOINC='" + getCodeLOINC() + "'" +
            ", category='" + getCategory() + "'" +
            ", codeVisit='" + getCodeVisit() + "'" +
            ", codeFdiOral='" + getCodeFdiOral() + "'" +
            ", timing='" + getTiming() + "'" +
            ", timingEnd='" + getTimingEnd() + "'" +
            ", valueBoolean='" + getValueBoolean() + "'" +
            ", valueString='" + getValueString() + "'" +
            ", reason='" + getReason() + "'" +
            ", reasonMissingTooth='" + getReasonMissingTooth() + "'" +
            "}";
    }
}
