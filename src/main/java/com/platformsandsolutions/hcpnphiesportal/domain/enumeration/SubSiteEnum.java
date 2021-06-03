package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The SubSiteEnum enumeration.
 */
public enum SubSiteEnum {
    R("Right"),
    L("Left"),
    U("Upper"),
    D("Down"),
    A("Anterior"),
    P("Posterior"),
    I("interior"),
    E("Exterior");

    private final String value;

    SubSiteEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
