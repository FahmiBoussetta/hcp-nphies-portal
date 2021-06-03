package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The TaskCodeEnum enumeration.
 */
public enum TaskCodeEnum {
    Cancel("Cancel the focal resource"),
    Nullify("Nullify the focal resource"),
    Poll("Poll the focal resource"),
    Release("Release the focal resource"),
    Reprocess("Reprocess the focal resource"),
    Status("Check status of the focal resource");

    private final String value;

    TaskCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
