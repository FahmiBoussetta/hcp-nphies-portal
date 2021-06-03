package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ClaimTypeEnum enumeration.
 */
public enum ClaimTypeEnum {
    Institutional("institutional"),
    Oral("oral"),
    Pharmacy("pharmacy"),
    Professional("professional"),
    Vision("vision");

    private final String value;

    ClaimTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
