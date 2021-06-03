package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimErrorMessages.class);
        ClaimErrorMessages claimErrorMessages1 = new ClaimErrorMessages();
        claimErrorMessages1.setId(1L);
        ClaimErrorMessages claimErrorMessages2 = new ClaimErrorMessages();
        claimErrorMessages2.setId(claimErrorMessages1.getId());
        assertThat(claimErrorMessages1).isEqualTo(claimErrorMessages2);
        claimErrorMessages2.setId(2L);
        assertThat(claimErrorMessages1).isNotEqualTo(claimErrorMessages2);
        claimErrorMessages1.setId(null);
        assertThat(claimErrorMessages1).isNotEqualTo(claimErrorMessages2);
    }
}
