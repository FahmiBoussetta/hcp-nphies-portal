package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The CostToBeneficiaryTypeEnum enumeration.
 */
public enum CostToBeneficiaryTypeEnum {
    Gpvisit("An office visit for a general practitioner of a discipline."),
    Spvisit("An office visit for a specialist practitioner of a discipline"),
    Copaypct("A standard percentage applied to all classes or service or product not otherwise specified."),
    Copay("A standard fixed currency amount applied to all classes or service or product not otherwise specified."),
    Deductible("The accumulated amount of patient payment before the coverage begins to pay for services."),
    Maxoutofpocket("The maximum amout of payment for services which a patient or family is expected to incur - typically annually");

    private final String value;

    CostToBeneficiaryTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
