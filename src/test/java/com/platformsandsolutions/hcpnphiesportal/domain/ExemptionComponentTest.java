package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExemptionComponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExemptionComponent.class);
        ExemptionComponent exemptionComponent1 = new ExemptionComponent();
        exemptionComponent1.setId(1L);
        ExemptionComponent exemptionComponent2 = new ExemptionComponent();
        exemptionComponent2.setId(exemptionComponent1.getId());
        assertThat(exemptionComponent1).isEqualTo(exemptionComponent2);
        exemptionComponent2.setId(2L);
        assertThat(exemptionComponent1).isNotEqualTo(exemptionComponent2);
        exemptionComponent1.setId(null);
        assertThat(exemptionComponent1).isNotEqualTo(exemptionComponent2);
    }
}
