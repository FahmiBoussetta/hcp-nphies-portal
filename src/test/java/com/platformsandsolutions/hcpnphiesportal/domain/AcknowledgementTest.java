package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcknowledgementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acknowledgement.class);
        Acknowledgement acknowledgement1 = new Acknowledgement();
        acknowledgement1.setId(1L);
        Acknowledgement acknowledgement2 = new Acknowledgement();
        acknowledgement2.setId(acknowledgement1.getId());
        assertThat(acknowledgement1).isEqualTo(acknowledgement2);
        acknowledgement2.setId(2L);
        assertThat(acknowledgement1).isNotEqualTo(acknowledgement2);
        acknowledgement1.setId(null);
        assertThat(acknowledgement1).isNotEqualTo(acknowledgement2);
    }
}
