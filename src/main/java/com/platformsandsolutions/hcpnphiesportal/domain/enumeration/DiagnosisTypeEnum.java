package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The DiagnosisTypeEnum enumeration.
 */
public enum DiagnosisTypeEnum {
    Admitting("Admitting Diagnosis"),
    Clinical("Clinical Diagnosis"),
    Differential("Differential Diagnosis"),
    Discharge("Discharge Diagnosis"),
    Laboratory("Laboratory Diagnosis"),
    Nursing("Nursing Diagnosis"),
    Prenatal("Prenatal Diagnosis"),
    Principal("Principal Diagnosis"),
    Radiology("Radiology Diagnosis"),
    Remote("Remote Diagnosis"),
    Retrospective("Retrospective Diagnosis"),
    Self("Self Diagnosis");

    private final String value;

    DiagnosisTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
