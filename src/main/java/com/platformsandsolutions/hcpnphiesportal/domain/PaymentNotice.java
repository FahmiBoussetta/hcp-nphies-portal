package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PaymentStatusEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentNotice.
 */
@Entity
@Table(name = "payment_notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatusEnum paymentStatus;

    @JsonIgnoreProperties(value = { "paymentIssuer", "details", "paymentNotice" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PaymentReconciliation payment;

    @OneToMany(mappedBy = "paymentNotice")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentNotice" }, allowSetters = true)
    private Set<PayNotErrorMessages> errors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentNotice id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public PaymentNotice guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParsed() {
        return this.parsed;
    }

    public PaymentNotice parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public PaymentNotice identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Instant getPaymentDate() {
        return this.paymentDate;
    }

    public PaymentNotice paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public PaymentNotice amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return this.paymentStatus;
    }

    public PaymentNotice paymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentReconciliation getPayment() {
        return this.payment;
    }

    public PaymentNotice payment(PaymentReconciliation paymentReconciliation) {
        this.setPayment(paymentReconciliation);
        return this;
    }

    public void setPayment(PaymentReconciliation paymentReconciliation) {
        this.payment = paymentReconciliation;
    }

    public Set<PayNotErrorMessages> getErrors() {
        return this.errors;
    }

    public PaymentNotice errors(Set<PayNotErrorMessages> payNotErrorMessages) {
        this.setErrors(payNotErrorMessages);
        return this;
    }

    public PaymentNotice addErrors(PayNotErrorMessages payNotErrorMessages) {
        this.errors.add(payNotErrorMessages);
        payNotErrorMessages.setPaymentNotice(this);
        return this;
    }

    public PaymentNotice removeErrors(PayNotErrorMessages payNotErrorMessages) {
        this.errors.remove(payNotErrorMessages);
        payNotErrorMessages.setPaymentNotice(null);
        return this;
    }

    public void setErrors(Set<PayNotErrorMessages> payNotErrorMessages) {
        if (this.errors != null) {
            this.errors.forEach(i -> i.setPaymentNotice(null));
        }
        if (payNotErrorMessages != null) {
            payNotErrorMessages.forEach(i -> i.setPaymentNotice(this));
        }
        this.errors = payNotErrorMessages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentNotice)) {
            return false;
        }
        return id != null && id.equals(((PaymentNotice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentNotice{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", amount=" + getAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
