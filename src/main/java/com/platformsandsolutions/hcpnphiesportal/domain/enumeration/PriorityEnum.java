package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The PriorityEnum enumeration.
 */
public enum PriorityEnum {
    Stat("Immediate"),
    Normal,
    Deferred;

    private final String value;

    PriorityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
