package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoverageEligibilityResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoverageEligibilityResponse.class);
        CoverageEligibilityResponse coverageEligibilityResponse1 = new CoverageEligibilityResponse();
        coverageEligibilityResponse1.setId(1L);
        CoverageEligibilityResponse coverageEligibilityResponse2 = new CoverageEligibilityResponse();
        coverageEligibilityResponse2.setId(coverageEligibilityResponse1.getId());
        assertThat(coverageEligibilityResponse1).isEqualTo(coverageEligibilityResponse2);
        coverageEligibilityResponse2.setId(2L);
        assertThat(coverageEligibilityResponse1).isNotEqualTo(coverageEligibilityResponse2);
        coverageEligibilityResponse1.setId(null);
        assertThat(coverageEligibilityResponse1).isNotEqualTo(coverageEligibilityResponse2);
    }
}
