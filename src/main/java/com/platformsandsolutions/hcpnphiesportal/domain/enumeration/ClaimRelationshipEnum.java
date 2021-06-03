package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ClaimRelationshipEnum enumeration.
 */
public enum ClaimRelationshipEnum {
    Prior("Prior Claim"),
    Associated("Associated Claim"),
    Extend("Authorization to extend");

    private final String value;

    ClaimRelationshipEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
