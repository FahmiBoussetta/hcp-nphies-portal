package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ActPriorityEnum enumeration.
 */
public enum ActPriorityEnum {
    EM("Emergency"),
    EL("Elective");

    private final String value;

    ActPriorityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
