package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The OrganizationTypeEnum enumeration.
 */
public enum OrganizationTypeEnum {
    Prov("Healthcare Provider"),
    Dept("Hospital Department"),
    Ins("Insurance Company"),
    Pay("Payer"),
    Other;

    private final String value;

    OrganizationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
