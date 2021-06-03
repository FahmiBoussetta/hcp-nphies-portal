package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The Use enumeration.
 */
public enum Use {
    Claim("claim"),
    PreAuthorization("preauthorization"),
    Predetermination("predetermination");

    private final String value;

    Use(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
