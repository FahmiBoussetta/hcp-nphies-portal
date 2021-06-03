package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayloadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payload.class);
        Payload payload1 = new Payload();
        payload1.setId(1L);
        Payload payload2 = new Payload();
        payload2.setId(payload1.getId());
        assertThat(payload1).isEqualTo(payload2);
        payload2.setId(2L);
        assertThat(payload1).isNotEqualTo(payload2);
        payload1.setId(null);
        assertThat(payload1).isNotEqualTo(payload2);
    }
}
