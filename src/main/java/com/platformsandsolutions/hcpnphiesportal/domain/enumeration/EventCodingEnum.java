package com.platformsandsolutions.hcpnphiesportal.domain.enumeration;

/**
 * The EventCodingEnum enumeration.
 */
public enum EventCodingEnum {
    Eligibility_request("Eligibility Request"),
    Eligibility_response("Eligibility Response"),
    Priorauth_request("Prior Authorization Request"),
    Priorauth_response("Prior Authorization Response"),
    Claim_request("Claim Request"),
    Claim_response("Claim Response"),
    Batch_request("Batch-Request"),
    Status_check("Status Check"),
    Status_response("Status Response"),
    Cancel_request("Cancel Request"),
    Cancel_response("Cancel Response"),
    Payment_notice("Payment Notice"),
    Payment_reconciliation("Payment Reconciliation"),
    Communication_request("Communication Request"),
    Communication,
    Acknowledgement,
    Poll_request("Poll Request"),
    Poll_response("Poll Response"),
    Nullify_request("Nullify Request"),
    Nullify_response("Nullify Response");

    private final String value;

    EventCodingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
