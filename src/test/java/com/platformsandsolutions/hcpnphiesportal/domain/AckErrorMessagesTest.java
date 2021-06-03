package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AckErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AckErrorMessages.class);
        AckErrorMessages ackErrorMessages1 = new AckErrorMessages();
        ackErrorMessages1.setId(1L);
        AckErrorMessages ackErrorMessages2 = new AckErrorMessages();
        ackErrorMessages2.setId(ackErrorMessages1.getId());
        assertThat(ackErrorMessages1).isEqualTo(ackErrorMessages2);
        ackErrorMessages2.setId(2L);
        assertThat(ackErrorMessages1).isNotEqualTo(ackErrorMessages2);
        ackErrorMessages1.setId(null);
        assertThat(ackErrorMessages1).isNotEqualTo(ackErrorMessages2);
    }
}
