package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ExemptionTypeEnum enumeration.
 */
public enum ExemptionTypeEnum {
    Retired,
    Foster("Foster child");

    private final String value;

    ExemptionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
