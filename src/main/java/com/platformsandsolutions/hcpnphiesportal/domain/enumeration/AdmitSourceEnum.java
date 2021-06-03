package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The AdmitSourceEnum enumeration.
 */
public enum AdmitSourceEnum {
    IA("Immediate Admission"),
    EER("Admission from hospital ER"),
    EOP("Emergency Admission from hospital outpatient"),
    EGPHC("Emergency Admission by referral from government primary healthcare center"),
    EGGH("Emergency Admission by referral from general government hospital"),
    EPPHC("Emergency Admission by referral from private primary healthcare center"),
    EPH("Emergency Admission by referral from private hospital"),
    EIC("Emergency Admission by insurance company"),
    EWGS("Elective waiting list admission government free Scheme"),
    EWSS("Elective waiting list admission self-payment Scheme"),
    EWIS("Elective waiting list admission insurance coverage Scheme"),
    EMBA("Emergency Maternity Birth Admission"),
    PMBA("Planned Maternity Birth Admission"),
    Others;

    private final String value;

    AdmitSourceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
