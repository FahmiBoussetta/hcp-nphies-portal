package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupportingInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportingInfo.class);
        SupportingInfo supportingInfo1 = new SupportingInfo();
        supportingInfo1.setId(1L);
        SupportingInfo supportingInfo2 = new SupportingInfo();
        supportingInfo2.setId(supportingInfo1.getId());
        assertThat(supportingInfo1).isEqualTo(supportingInfo2);
        supportingInfo2.setId(2L);
        assertThat(supportingInfo1).isNotEqualTo(supportingInfo2);
        supportingInfo1.setId(null);
        assertThat(supportingInfo1).isNotEqualTo(supportingInfo2);
    }
}
