package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The DiagnosisOnAdmissionEnum enumeration.
 */
public enum DiagnosisOnAdmissionEnum {
    Y("Yes"),
    N("No"),
    U("Unknown");

    private final String value;

    DiagnosisOnAdmissionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
