package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HospitalizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hospitalization.class);
        Hospitalization hospitalization1 = new Hospitalization();
        hospitalization1.setId(1L);
        Hospitalization hospitalization2 = new Hospitalization();
        hospitalization2.setId(hospitalization1.getId());
        assertThat(hospitalization1).isEqualTo(hospitalization2);
        hospitalization2.setId(2L);
        assertThat(hospitalization1).isNotEqualTo(hospitalization2);
        hospitalization1.setId(null);
        assertThat(hospitalization1).isNotEqualTo(hospitalization2);
    }
}
