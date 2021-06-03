package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The SupportingInfoCategoryEnum enumeration.
 */
public enum SupportingInfoCategoryEnum {
    Info("Information"),
    Discharge,
    Onset,
    Related("Related Services"),
    Exception,
    Material("Materials Forwarded"),
    Attachment,
    Missingtooth("Missing Tooth"),
    Prosthesis,
    Other,
    Hospitalized,
    EmploymentImpacted,
    External_Cause("External Caause"),
    Patient_Reason_for_Visit("Patient Reason for Visit"),
    Lab_test("lab-test"),
    Reason_for_Visit("Reason for visit"),
    Days_Supply("Days supply"),
    Vital_Sign_Weight("Vital Sign Weight"),
    Vital_Sign_Systolic("Vital Sign Systolic"),
    Vital_Sign_Diastolic("Vital Sign Diastolic"),
    Icu_hours("ICU Hours"),
    Ventilation_hours("Ventilation Hours"),
    Vital_Sign_Height("Vital Sign Height");

    private final String value;

    SupportingInfoCategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
