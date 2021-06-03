package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The SupportingInfoReasonEnum enumeration.
 */
public enum SupportingInfoReasonEnum {
    Missing_info("Missing information"),
    Missing_attach("Missing attachement"),
    Info_Correct("Information correction");

    private final String value;

    SupportingInfoReasonEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
