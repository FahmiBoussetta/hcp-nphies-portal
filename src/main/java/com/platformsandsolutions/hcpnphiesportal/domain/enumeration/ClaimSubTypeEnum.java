package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ClaimSubTypeEnum enumeration.
 */
public enum ClaimSubTypeEnum {
    Ip("InPatient"),
    Op("OutPatient"),
    Emr("Emergency");

    private final String value;

    ClaimSubTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
