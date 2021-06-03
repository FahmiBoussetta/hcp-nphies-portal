package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The SupportingInfoReasonMissingToothEnum enumeration.
 */
public enum SupportingInfoReasonMissingToothEnum {
    E("Extraction"),
    C("Congenital"),
    U("Unknown"),
    O("Other");

    private final String value;

    SupportingInfoReasonMissingToothEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
