package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentReconciliation.
 */
@Entity
@Table(name = "payment_reconciliation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentReconciliation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "period_start")
    private Instant periodStart;

    @Column(name = "period_end")
    private Instant periodEnd;

    @Column(name = "outcome")
    private String outcome;

    @Column(name = "disposition")
    private String disposition;

    @Column(name = "payment_amount", precision = 21, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "payment_identifier")
    private String paymentIdentifier;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization paymentIssuer;

    @OneToMany(mappedBy = "paymentReconciliation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "request", "submitter", "response", "payee", "paymentReconciliation" }, allowSetters = true)
    private Set<ReconciliationDetailItem> details = new HashSet<>();

    @JsonIgnoreProperties(value = { "payment", "errors" }, allowSetters = true)
    @OneToOne(mappedBy = "payment")
    private PaymentNotice paymentNotice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentReconciliation id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public PaymentReconciliation value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSystem() {
        return this.system;
    }

    public PaymentReconciliation system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getParsed() {
        return this.parsed;
    }

    public PaymentReconciliation parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public Instant getPeriodStart() {
        return this.periodStart;
    }

    public PaymentReconciliation periodStart(Instant periodStart) {
        this.periodStart = periodStart;
        return this;
    }

    public void setPeriodStart(Instant periodStart) {
        this.periodStart = periodStart;
    }

    public Instant getPeriodEnd() {
        return this.periodEnd;
    }

    public PaymentReconciliation periodEnd(Instant periodEnd) {
        this.periodEnd = periodEnd;
        return this;
    }

    public void setPeriodEnd(Instant periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getOutcome() {
        return this.outcome;
    }

    public PaymentReconciliation outcome(String outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getDisposition() {
        return this.disposition;
    }

    public PaymentReconciliation disposition(String disposition) {
        this.disposition = disposition;
        return this;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public BigDecimal getPaymentAmount() {
        return this.paymentAmount;
    }

    public PaymentReconciliation paymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentIdentifier() {
        return this.paymentIdentifier;
    }

    public PaymentReconciliation paymentIdentifier(String paymentIdentifier) {
        this.paymentIdentifier = paymentIdentifier;
        return this;
    }

    public void setPaymentIdentifier(String paymentIdentifier) {
        this.paymentIdentifier = paymentIdentifier;
    }

    public Organization getPaymentIssuer() {
        return this.paymentIssuer;
    }

    public PaymentReconciliation paymentIssuer(Organization organization) {
        this.setPaymentIssuer(organization);
        return this;
    }

    public void setPaymentIssuer(Organization organization) {
        this.paymentIssuer = organization;
    }

    public Set<ReconciliationDetailItem> getDetails() {
        return this.details;
    }

    public PaymentReconciliation details(Set<ReconciliationDetailItem> reconciliationDetailItems) {
        this.setDetails(reconciliationDetailItems);
        return this;
    }

    public PaymentReconciliation addDetail(ReconciliationDetailItem reconciliationDetailItem) {
        this.details.add(reconciliationDetailItem);
        reconciliationDetailItem.setPaymentReconciliation(this);
        return this;
    }

    public PaymentReconciliation removeDetail(ReconciliationDetailItem reconciliationDetailItem) {
        this.details.remove(reconciliationDetailItem);
        reconciliationDetailItem.setPaymentReconciliation(null);
        return this;
    }

    public void setDetails(Set<ReconciliationDetailItem> reconciliationDetailItems) {
        if (this.details != null) {
            this.details.forEach(i -> i.setPaymentReconciliation(null));
        }
        if (reconciliationDetailItems != null) {
            reconciliationDetailItems.forEach(i -> i.setPaymentReconciliation(this));
        }
        this.details = reconciliationDetailItems;
    }

    public PaymentNotice getPaymentNotice() {
        return this.paymentNotice;
    }

    public PaymentReconciliation paymentNotice(PaymentNotice paymentNotice) {
        this.setPaymentNotice(paymentNotice);
        return this;
    }

    public void setPaymentNotice(PaymentNotice paymentNotice) {
        if (this.paymentNotice != null) {
            this.paymentNotice.setPayment(null);
        }
        if (paymentNotice != null) {
            paymentNotice.setPayment(this);
        }
        this.paymentNotice = paymentNotice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentReconciliation)) {
            return false;
        }
        return id != null && id.equals(((PaymentReconciliation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentReconciliation{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", system='" + getSystem() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", periodStart='" + getPeriodStart() + "'" +
            ", periodEnd='" + getPeriodEnd() + "'" +
            ", outcome='" + getOutcome() + "'" +
            ", disposition='" + getDisposition() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", paymentIdentifier='" + getPaymentIdentifier() + "'" +
            "}";
    }
}
