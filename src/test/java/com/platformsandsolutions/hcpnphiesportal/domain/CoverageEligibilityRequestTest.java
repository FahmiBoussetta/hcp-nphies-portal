package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoverageEligibilityRequestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoverageEligibilityRequest.class);
        CoverageEligibilityRequest coverageEligibilityRequest1 = new CoverageEligibilityRequest();
        coverageEligibilityRequest1.setId(1L);
        CoverageEligibilityRequest coverageEligibilityRequest2 = new CoverageEligibilityRequest();
        coverageEligibilityRequest2.setId(coverageEligibilityRequest1.getId());
        assertThat(coverageEligibilityRequest1).isEqualTo(coverageEligibilityRequest2);
        coverageEligibilityRequest2.setId(2L);
        assertThat(coverageEligibilityRequest1).isNotEqualTo(coverageEligibilityRequest2);
        coverageEligibilityRequest1.setId(null);
        assertThat(coverageEligibilityRequest1).isNotEqualTo(coverageEligibilityRequest2);
    }
}
