package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The DischargeDispositionEnum enumeration.
 */
public enum DischargeDispositionEnum {
    Home,
    DASHalt_home("Alternative home"),
    DASHother_hcf("Other healthcare facility"),
    Hosp("Hospice"),
    DASHlong("Long-term care"),
    Aadvice("Left against advice"),
    Exp("Expired"),
    Psy("Psychiatric hospital"),
    Rehab("Rehabilitation"),
    Snf("Skilled nursing facility"),
    Oth("Other");

    private final String value;

    DischargeDispositionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
