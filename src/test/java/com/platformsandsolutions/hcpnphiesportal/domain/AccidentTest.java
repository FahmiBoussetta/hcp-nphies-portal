package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccidentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accident.class);
        Accident accident1 = new Accident();
        accident1.setId(1L);
        Accident accident2 = new Accident();
        accident2.setId(accident1.getId());
        assertThat(accident1).isEqualTo(accident2);
        accident2.setId(2L);
        assertThat(accident1).isNotEqualTo(accident2);
        accident1.setId(null);
        assertThat(accident1).isNotEqualTo(accident2);
    }
}
