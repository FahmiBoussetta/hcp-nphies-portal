package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The LanguageEnum enumeration.
 */
public enum LanguageEnum {
    AR("Arabic"),
    EN("English");

    private final String value;

    LanguageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
