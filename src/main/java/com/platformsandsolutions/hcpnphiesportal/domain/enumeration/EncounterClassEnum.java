package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The EncounterClassEnum enumeration.
 */
public enum EncounterClassEnum {
    AMB("ambulatory"),
    EMER("emergency"),
    HH("home health"),
    IMP("inpatient encounter"),
    SS("short stay");

    private final String value;

    EncounterClassEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
