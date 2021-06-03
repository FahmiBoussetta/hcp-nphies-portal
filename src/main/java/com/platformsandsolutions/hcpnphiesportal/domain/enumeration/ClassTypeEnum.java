package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ClassTypeEnum enumeration.
 */
public enum ClassTypeEnum {
    Group,
    Subgroup("SubGroup"),
    Plan,
    Subplan("SubPlan"),
    Class,
    Subclass("SubClass"),
    Sequence,
    Rxbin("RX BIN"),
    Rxpcn("RX PCN"),
    Rxid("RX Id"),
    Rxgroup("RX Group");

    private final String value;

    ClassTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
