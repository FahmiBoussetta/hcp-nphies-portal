package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ReligionEnum enumeration.
 */
public enum ReligionEnum {
    N0("Other"),
    N1("Muslim"),
    N2("Christian"),
    N3("Judaism"),
    N4("Buddhism"),
    N5("Zoroastrian"),
    N7("Hinduism"),
    N8("Sikh"),
    N9("Without"),
    N98("Not available"),
    N99("Not Mentioned");

    private final String value;

    ReligionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
