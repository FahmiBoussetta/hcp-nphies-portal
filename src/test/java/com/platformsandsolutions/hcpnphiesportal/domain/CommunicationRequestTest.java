package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommunicationRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommunicationRequest.class);
        CommunicationRequest communicationRequest1 = new CommunicationRequest();
        communicationRequest1.setId(1L);
        CommunicationRequest communicationRequest2 = new CommunicationRequest();
        communicationRequest2.setId(communicationRequest1.getId());
        assertThat(communicationRequest1).isEqualTo(communicationRequest2);
        communicationRequest2.setId(2L);
        assertThat(communicationRequest1).isNotEqualTo(communicationRequest2);
        communicationRequest1.setId(null);
        assertThat(communicationRequest1).isNotEqualTo(communicationRequest2);
    }
}
