package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationOutcomeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationOutcome.class);
        OperationOutcome operationOutcome1 = new OperationOutcome();
        operationOutcome1.setId(1L);
        OperationOutcome operationOutcome2 = new OperationOutcome();
        operationOutcome2.setId(operationOutcome1.getId());
        assertThat(operationOutcome1).isEqualTo(operationOutcome2);
        operationOutcome2.setId(2L);
        assertThat(operationOutcome1).isNotEqualTo(operationOutcome2);
        operationOutcome1.setId(null);
        assertThat(operationOutcome1).isNotEqualTo(operationOutcome2);
    }
}
