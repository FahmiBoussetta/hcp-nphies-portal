package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The ResourceTypeEnum enumeration.
 */
public enum ResourceTypeEnum {
    Claim,
    ClaimResponse,
    Communication,
    CommunicationRequest,
    CoverageEligibilityRequest,
    CoverageEligibilityResponse,
    OperationOutcome,
    PaymentNotice,
    PaymentReconciliation;

    private final String value;

    ResourceTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
