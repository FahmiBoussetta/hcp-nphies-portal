package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ReAdmissionEnum enumeration.
 */
public enum ReAdmissionEnum {
    R("Re-admission");

    private final String value;

    ReAdmissionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
