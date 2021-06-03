package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The CommunicationMediumEnum enumeration.
 */
public enum CommunicationMediumEnum {
    ELECTRONIC("electronic data"),
    PHYSICAL("physical presence"),
    REMOTE("remote presence"),
    VERBAL("verbal"),
    DICTATE("dictated"),
    FACE("face-to-face"),
    PHONE("telephone"),
    VIDEOCONF("videoconferencing"),
    WRITTEN("written"),
    FAXWRIT("telefax"),
    HANDWRIT("handwritten"),
    MAILWRIT("mail"),
    ONLINEWRIT("online written"),
    EMAILWRIT("email"),
    TYPEWRIT("typewritten");

    private final String value;

    CommunicationMediumEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
