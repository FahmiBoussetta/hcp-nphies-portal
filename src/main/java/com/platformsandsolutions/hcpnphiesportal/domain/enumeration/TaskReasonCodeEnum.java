package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The TaskReasonCodeEnum enumeration.
 */
public enum TaskReasonCodeEnum {
    WI("wrong information"),
    NP("service not performed"),
    TAS("transaction already submitted");

    private final String value;

    TaskReasonCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
