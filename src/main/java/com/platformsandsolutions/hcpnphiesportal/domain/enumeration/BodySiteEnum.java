package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The BodySiteEnum enumeration.
 */
public enum BodySiteEnum {
    E1("Upper left eyelid"),
    E2("Lower left eyelid"),
    E3("Upper right eyelid"),
    E4("Lower right eyelid"),
    F1("Left hand second digit"),
    F2("Left hand third digit"),
    F3("Left hand fourth digit"),
    F4("Left hand fifth digit"),
    F5("Right hand thumb"),
    F6("Right hand second digit"),
    F7("Right hand third digit"),
    F8("Right hand fourth digit"),
    F9("Right hand fifth digit"),
    FA("Left hand thumb"),
    LC("Left circumflex coronary artery"),
    LD("Left anterior descending coronary artery"),
    LM("Left main coronary artery"),
    LT("Left side (used to identify procedures performed on the left side of the body"),
    RC("Right coronary artery"),
    RI("Ramus intermedius coronary artery"),
    RT("Right side (used to identify procedures performed on the right side of the body"),
    T1("Left foot second digit"),
    T2("Left foot third digit"),
    T3("Left foot fourth digit"),
    T4("Left foot fifth digit"),
    T5("Right foot great toe"),
    T6("Right foot second digit"),
    T7("Right foot third digit"),
    T8("Right foot fourth digit"),
    T9("Right foot fifth digit"),
    TA("Left foot great toe");

    private final String value;

    BodySiteEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
