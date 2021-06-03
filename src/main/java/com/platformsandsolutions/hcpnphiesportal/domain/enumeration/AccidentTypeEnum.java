package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The AccidentTypeEnum enumeration.
 */
public enum AccidentTypeEnum {
    MVA("Motor vehicle accident"),
    SCHOOL("School Accident"),
    SPT("Sporting Accident"),
    WPA("Workplace accident");

    private final String value;

    AccidentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
