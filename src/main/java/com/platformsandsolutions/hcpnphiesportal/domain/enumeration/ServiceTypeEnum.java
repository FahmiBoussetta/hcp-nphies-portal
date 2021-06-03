package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ServiceTypeEnum enumeration.
 */
public enum ServiceTypeEnum {
    N237("Acute Inpatient Serv"),
    N576("Rehabilitation"),
    N356("General Maintenance"),
    N621("Complex Maintenance"),
    N179("Palliative medicine");

    private final String value;

    ServiceTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
