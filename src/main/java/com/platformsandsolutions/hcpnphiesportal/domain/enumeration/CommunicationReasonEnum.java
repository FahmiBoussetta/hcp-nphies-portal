package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The CommunicationReasonEnum enumeration.
 */
public enum CommunicationReasonEnum {
    Missing_info("Missing information"),
    Missing_attach("Missing attachment"),
    Info_correct("Information correction");

    private final String value;

    CommunicationReasonEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
