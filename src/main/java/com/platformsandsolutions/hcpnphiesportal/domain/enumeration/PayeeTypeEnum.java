package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The PayeeTypeEnum enumeration.
 */
public enum PayeeTypeEnum {
    Subscriber,
    Provider,
    Other;

    private final String value;

    PayeeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
