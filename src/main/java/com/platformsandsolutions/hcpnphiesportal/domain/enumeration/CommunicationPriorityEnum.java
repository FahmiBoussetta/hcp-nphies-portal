package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The CommunicationPriorityEnum enumeration.
 */
public enum CommunicationPriorityEnum {
    Stat("Immediate - Priority 1"),
    Asap("As soon as possible - Priority 2"),
    Urgent("Urgent - Priority 3"),
    Routine("Routine - Priority 4");

    private final String value;

    CommunicationPriorityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
