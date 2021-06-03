package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The RelationShipEnum enumeration.
 */
public enum RelationShipEnum {
    Child,
    Parent,
    Spouse,
    Common("Common Law Spouse"),
    Other,
    Self,
    Injured;

    private final String value;

    RelationShipEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
