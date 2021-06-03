package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The AdministrativeGenderEnum enumeration.
 */
public enum AdministrativeGenderEnum {
    Male,
    Female,
    Unknown,
    U("Undetermined"),
    N("Undifferentiated"),
    A("Sex changed to Male"),
    B("Sex changed to female "),
    C("Not Completed");

    private final String value;

    AdministrativeGenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
