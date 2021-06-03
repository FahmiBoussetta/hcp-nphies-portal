package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClaimSubTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClaimTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.FundsReserveEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PriorityEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.Use;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Claim.
 */
@Entity
@Table(name = "claim")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Claim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "is_queued")
    private Boolean isQueued;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "identifier")
    private String identifier;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_use", nullable = false)
    private Use use;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ClaimTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_type")
    private ClaimSubTypeEnum subType;

    @Column(name = "eligibility_offline")
    private String eligibilityOffline;

    @Column(name = "eligibility_offline_date")
    private Instant eligibilityOfflineDate;

    @Column(name = "authorization_offline_date")
    private Instant authorizationOfflineDate;

    @Column(name = "billable_start")
    private Instant billableStart;

    @Column(name = "billable_end")
    private Instant billableEnd;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "funds_reserve")
    private FundsReserveEnum fundsReserve;

    @JsonIgnoreProperties(value = { "subject", "hospitalization", "serviceProvider" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Encounter encounter;

    @JsonIgnoreProperties(value = { "patient", "insurer", "errors", "insurances" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CoverageEligibilityResponse eligibilityResponse;

    @JsonIgnoreProperties(value = { "contacts", "address", "names" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Patient patient;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization provider;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization insurer;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier prescription;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier originalPrescription;

    @JsonIgnoreProperties(value = { "partyPatient", "partyOrganization" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Payee payee;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier referral;

    @JsonIgnoreProperties(value = { "managingOrganization" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Location facility;

    @JsonIgnoreProperties(value = { "location" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Accident accident;

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "claim" }, allowSetters = true)
    private Set<Diagnosis> diagnoses = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coverage", "claimResponse", "claim" }, allowSetters = true)
    private Set<Insurance> insurances = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "diagnosisSequences", "informationSequences", "udis", "details", "claim" }, allowSetters = true)
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "claim" }, allowSetters = true)
    private Set<ClaimErrorMessages> errors = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "claimReference", "claim" }, allowSetters = true)
    private Set<Related> relateds = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "provider", "providerRole", "claim" }, allowSetters = true)
    private Set<CareTeam> careTeams = new HashSet<>();

    @OneToMany(mappedBy = "claim")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "valueQuantity", "valueAttachment", "valueReference", "claim" }, allowSetters = true)
    private Set<SupportingInfo> supportingInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Claim id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Claim guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Boolean getIsQueued() {
        return this.isQueued;
    }

    public Claim isQueued(Boolean isQueued) {
        this.isQueued = isQueued;
        return this;
    }

    public void setIsQueued(Boolean isQueued) {
        this.isQueued = isQueued;
    }

    public String getParsed() {
        return this.parsed;
    }

    public Claim parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Claim identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Use getUse() {
        return this.use;
    }

    public Claim use(Use use) {
        this.use = use;
        return this;
    }

    public void setUse(Use use) {
        this.use = use;
    }

    public ClaimTypeEnum getType() {
        return this.type;
    }

    public Claim type(ClaimTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(ClaimTypeEnum type) {
        this.type = type;
    }

    public ClaimSubTypeEnum getSubType() {
        return this.subType;
    }

    public Claim subType(ClaimSubTypeEnum subType) {
        this.subType = subType;
        return this;
    }

    public void setSubType(ClaimSubTypeEnum subType) {
        this.subType = subType;
    }

    public String getEligibilityOffline() {
        return this.eligibilityOffline;
    }

    public Claim eligibilityOffline(String eligibilityOffline) {
        this.eligibilityOffline = eligibilityOffline;
        return this;
    }

    public void setEligibilityOffline(String eligibilityOffline) {
        this.eligibilityOffline = eligibilityOffline;
    }

    public Instant getEligibilityOfflineDate() {
        return this.eligibilityOfflineDate;
    }

    public Claim eligibilityOfflineDate(Instant eligibilityOfflineDate) {
        this.eligibilityOfflineDate = eligibilityOfflineDate;
        return this;
    }

    public void setEligibilityOfflineDate(Instant eligibilityOfflineDate) {
        this.eligibilityOfflineDate = eligibilityOfflineDate;
    }

    public Instant getAuthorizationOfflineDate() {
        return this.authorizationOfflineDate;
    }

    public Claim authorizationOfflineDate(Instant authorizationOfflineDate) {
        this.authorizationOfflineDate = authorizationOfflineDate;
        return this;
    }

    public void setAuthorizationOfflineDate(Instant authorizationOfflineDate) {
        this.authorizationOfflineDate = authorizationOfflineDate;
    }

    public Instant getBillableStart() {
        return this.billableStart;
    }

    public Claim billableStart(Instant billableStart) {
        this.billableStart = billableStart;
        return this;
    }

    public void setBillableStart(Instant billableStart) {
        this.billableStart = billableStart;
    }

    public Instant getBillableEnd() {
        return this.billableEnd;
    }

    public Claim billableEnd(Instant billableEnd) {
        this.billableEnd = billableEnd;
        return this;
    }

    public void setBillableEnd(Instant billableEnd) {
        this.billableEnd = billableEnd;
    }

    public PriorityEnum getPriority() {
        return this.priority;
    }

    public Claim priority(PriorityEnum priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public FundsReserveEnum getFundsReserve() {
        return this.fundsReserve;
    }

    public Claim fundsReserve(FundsReserveEnum fundsReserve) {
        this.fundsReserve = fundsReserve;
        return this;
    }

    public void setFundsReserve(FundsReserveEnum fundsReserve) {
        this.fundsReserve = fundsReserve;
    }

    public Encounter getEncounter() {
        return this.encounter;
    }

    public Claim encounter(Encounter encounter) {
        this.setEncounter(encounter);
        return this;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public CoverageEligibilityResponse getEligibilityResponse() {
        return this.eligibilityResponse;
    }

    public Claim eligibilityResponse(CoverageEligibilityResponse coverageEligibilityResponse) {
        this.setEligibilityResponse(coverageEligibilityResponse);
        return this;
    }

    public void setEligibilityResponse(CoverageEligibilityResponse coverageEligibilityResponse) {
        this.eligibilityResponse = coverageEligibilityResponse;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public Claim patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Organization getProvider() {
        return this.provider;
    }

    public Claim provider(Organization organization) {
        this.setProvider(organization);
        return this;
    }

    public void setProvider(Organization organization) {
        this.provider = organization;
    }

    public Organization getInsurer() {
        return this.insurer;
    }

    public Claim insurer(Organization organization) {
        this.setInsurer(organization);
        return this;
    }

    public void setInsurer(Organization organization) {
        this.insurer = organization;
    }

    public ReferenceIdentifier getPrescription() {
        return this.prescription;
    }

    public Claim prescription(ReferenceIdentifier referenceIdentifier) {
        this.setPrescription(referenceIdentifier);
        return this;
    }

    public void setPrescription(ReferenceIdentifier referenceIdentifier) {
        this.prescription = referenceIdentifier;
    }

    public ReferenceIdentifier getOriginalPrescription() {
        return this.originalPrescription;
    }

    public Claim originalPrescription(ReferenceIdentifier referenceIdentifier) {
        this.setOriginalPrescription(referenceIdentifier);
        return this;
    }

    public void setOriginalPrescription(ReferenceIdentifier referenceIdentifier) {
        this.originalPrescription = referenceIdentifier;
    }

    public Payee getPayee() {
        return this.payee;
    }

    public Claim payee(Payee payee) {
        this.setPayee(payee);
        return this;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public ReferenceIdentifier getReferral() {
        return this.referral;
    }

    public Claim referral(ReferenceIdentifier referenceIdentifier) {
        this.setReferral(referenceIdentifier);
        return this;
    }

    public void setReferral(ReferenceIdentifier referenceIdentifier) {
        this.referral = referenceIdentifier;
    }

    public Location getFacility() {
        return this.facility;
    }

    public Claim facility(Location location) {
        this.setFacility(location);
        return this;
    }

    public void setFacility(Location location) {
        this.facility = location;
    }

    public Accident getAccident() {
        return this.accident;
    }

    public Claim accident(Accident accident) {
        this.setAccident(accident);
        return this;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Set<Diagnosis> getDiagnoses() {
        return this.diagnoses;
    }

    public Claim diagnoses(Set<Diagnosis> diagnoses) {
        this.setDiagnoses(diagnoses);
        return this;
    }

    public Claim addDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.add(diagnosis);
        diagnosis.setClaim(this);
        return this;
    }

    public Claim removeDiagnoses(Diagnosis diagnosis) {
        this.diagnoses.remove(diagnosis);
        diagnosis.setClaim(null);
        return this;
    }

    public void setDiagnoses(Set<Diagnosis> diagnoses) {
        if (this.diagnoses != null) {
            this.diagnoses.forEach(i -> i.setClaim(null));
        }
        if (diagnoses != null) {
            diagnoses.forEach(i -> i.setClaim(this));
        }
        this.diagnoses = diagnoses;
    }

    public Set<Insurance> getInsurances() {
        return this.insurances;
    }

    public Claim insurances(Set<Insurance> insurances) {
        this.setInsurances(insurances);
        return this;
    }

    public Claim addInsurances(Insurance insurance) {
        this.insurances.add(insurance);
        insurance.setClaim(this);
        return this;
    }

    public Claim removeInsurances(Insurance insurance) {
        this.insurances.remove(insurance);
        insurance.setClaim(null);
        return this;
    }

    public void setInsurances(Set<Insurance> insurances) {
        if (this.insurances != null) {
            this.insurances.forEach(i -> i.setClaim(null));
        }
        if (insurances != null) {
            insurances.forEach(i -> i.setClaim(this));
        }
        this.insurances = insurances;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public Claim items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public Claim addItems(Item item) {
        this.items.add(item);
        item.setClaim(this);
        return this;
    }

    public Claim removeItems(Item item) {
        this.items.remove(item);
        item.setClaim(null);
        return this;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setClaim(null));
        }
        if (items != null) {
            items.forEach(i -> i.setClaim(this));
        }
        this.items = items;
    }

    public Set<ClaimErrorMessages> getErrors() {
        return this.errors;
    }

    public Claim errors(Set<ClaimErrorMessages> claimErrorMessages) {
        this.setErrors(claimErrorMessages);
        return this;
    }

    public Claim addErrors(ClaimErrorMessages claimErrorMessages) {
        this.errors.add(claimErrorMessages);
        claimErrorMessages.setClaim(this);
        return this;
    }

    public Claim removeErrors(ClaimErrorMessages claimErrorMessages) {
        this.errors.remove(claimErrorMessages);
        claimErrorMessages.setClaim(null);
        return this;
    }

    public void setErrors(Set<ClaimErrorMessages> claimErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setClaim(null));
        }
        if (claimErrorMessages != null) {
            claimErrorMessages.forEach(i -> i.setClaim(this));
        }
        this.errors = claimErrorMessages;
    }

    public Set<Related> getRelateds() {
        return this.relateds;
    }

    public Claim relateds(Set<Related> relateds) {
        this.setRelateds(relateds);
        return this;
    }

    public Claim addRelateds(Related related) {
        this.relateds.add(related);
        related.setClaim(this);
        return this;
    }

    public Claim removeRelateds(Related related) {
        this.relateds.remove(related);
        related.setClaim(null);
        return this;
    }

    public void setRelateds(Set<Related> relateds) {
        if (this.relateds != null) {
            this.relateds.forEach(i -> i.setClaim(null));
        }
        if (relateds != null) {
            relateds.forEach(i -> i.setClaim(this));
        }
        this.relateds = relateds;
    }

    public Set<CareTeam> getCareTeams() {
        return this.careTeams;
    }

    public Claim careTeams(Set<CareTeam> careTeams) {
        this.setCareTeams(careTeams);
        return this;
    }

    public Claim addCareTeam(CareTeam careTeam) {
        this.careTeams.add(careTeam);
        careTeam.setClaim(this);
        return this;
    }

    public Claim removeCareTeam(CareTeam careTeam) {
        this.careTeams.remove(careTeam);
        careTeam.setClaim(null);
        return this;
    }

    public void setCareTeams(Set<CareTeam> careTeams) {
        if (this.careTeams != null) {
            this.careTeams.forEach(i -> i.setClaim(null));
        }
        if (careTeams != null) {
            careTeams.forEach(i -> i.setClaim(this));
        }
        this.careTeams = careTeams;
    }

    public Set<SupportingInfo> getSupportingInfos() {
        return this.supportingInfos;
    }

    public Claim supportingInfos(Set<SupportingInfo> supportingInfos) {
        this.setSupportingInfos(supportingInfos);
        return this;
    }

    public Claim addSupportingInfos(SupportingInfo supportingInfo) {
        this.supportingInfos.add(supportingInfo);
        supportingInfo.setClaim(this);
        return this;
    }

    public Claim removeSupportingInfos(SupportingInfo supportingInfo) {
        this.supportingInfos.remove(supportingInfo);
        supportingInfo.setClaim(null);
        return this;
    }

    public void setSupportingInfos(Set<SupportingInfo> supportingInfos) {
        if (this.supportingInfos != null) {
            this.supportingInfos.forEach(i -> i.setClaim(null));
        }
        if (supportingInfos != null) {
            supportingInfos.forEach(i -> i.setClaim(this));
        }
        this.supportingInfos = supportingInfos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Claim)) {
            return false;
        }
        return id != null && id.equals(((Claim) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Claim{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", isQueued='" + getIsQueued() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", use='" + getUse() + "'" +
            ", type='" + getType() + "'" +
            ", subType='" + getSubType() + "'" +
            ", eligibilityOffline='" + getEligibilityOffline() + "'" +
            ", eligibilityOfflineDate='" + getEligibilityOfflineDate() + "'" +
            ", authorizationOfflineDate='" + getAuthorizationOfflineDate() + "'" +
            ", billableStart='" + getBillableStart() + "'" +
            ", billableEnd='" + getBillableEnd() + "'" +
            ", priority='" + getPriority() + "'" +
            ", fundsReserve='" + getFundsReserve() + "'" +
            "}";
    }
}
