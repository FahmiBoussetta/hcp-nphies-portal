package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The CareTeamRoleEnum enumeration.
 */
public enum CareTeamRoleEnum {
    Primary("Primary provider"),
    Assist("Assisting Provider"),
    Supervisor("Supervising Provider"),
    Other;

    private final String value;

    CareTeamRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
