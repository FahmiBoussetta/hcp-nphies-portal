package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The SupportingInfoCodeVisitEnum enumeration.
 */
public enum SupportingInfoCodeVisitEnum {
    New_visit("New Visit"),
    Follow_up("Follow Up"),
    Refill,
    Walk_in("Walk in"),
    Referral;

    private final String value;

    SupportingInfoCodeVisitEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
