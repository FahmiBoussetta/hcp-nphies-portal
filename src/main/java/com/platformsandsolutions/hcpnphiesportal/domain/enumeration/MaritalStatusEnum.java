package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The MaritalStatusEnum enumeration.
 */
public enum MaritalStatusEnum {
    L("Legally separated"),
    D("Divorced"),
    M("Married"),
    U("Unmarried"),
    W("Widowed"),
    UNK("Unknown");

    private final String value;

    MaritalStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
