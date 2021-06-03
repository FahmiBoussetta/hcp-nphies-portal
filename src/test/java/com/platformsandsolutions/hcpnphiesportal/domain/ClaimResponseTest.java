package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClaimResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClaimResponse.class);
        ClaimResponse claimResponse1 = new ClaimResponse();
        claimResponse1.setId(1L);
        ClaimResponse claimResponse2 = new ClaimResponse();
        claimResponse2.setId(claimResponse1.getId());
        assertThat(claimResponse1).isEqualTo(claimResponse2);
        claimResponse2.setId(2L);
        assertThat(claimResponse1).isNotEqualTo(claimResponse2);
        claimResponse1.setId(null);
        assertThat(claimResponse1).isNotEqualTo(claimResponse2);
    }
}
