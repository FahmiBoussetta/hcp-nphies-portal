package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PractitionerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Practitioner.class);
        Practitioner practitioner1 = new Practitioner();
        practitioner1.setId(1L);
        Practitioner practitioner2 = new Practitioner();
        practitioner2.setId(practitioner1.getId());
        assertThat(practitioner1).isEqualTo(practitioner2);
        practitioner2.setId(2L);
        assertThat(practitioner1).isNotEqualTo(practitioner2);
        practitioner1.setId(null);
        assertThat(practitioner1).isNotEqualTo(practitioner2);
    }
}
