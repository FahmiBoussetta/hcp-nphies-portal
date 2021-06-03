package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The PaymentStatusEnum enumeration.
 */
public enum PaymentStatusEnum {
    Paid,
    Cleared;

    private final String value;

    PaymentStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
