package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The EligibilityPurposeEnum enumeration.
 */
public enum EligibilityPurposeEnum {
    Benefits("Coverage benefits"),
    Discovery("Coverage Discovery"),
    Validation("Coverage Validation");

    private final String value;

    EligibilityPurposeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
