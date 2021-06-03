package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HumanNameTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HumanName.class);
        HumanName humanName1 = new HumanName();
        humanName1.setId(1L);
        HumanName humanName2 = new HumanName();
        humanName2.setId(humanName1.getId());
        assertThat(humanName1).isEqualTo(humanName2);
        humanName2.setId(2L);
        assertThat(humanName1).isNotEqualTo(humanName2);
        humanName1.setId(null);
        assertThat(humanName1).isNotEqualTo(humanName2);
    }
}
