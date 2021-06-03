package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InformationSequenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformationSequence.class);
        InformationSequence informationSequence1 = new InformationSequence();
        informationSequence1.setId(1L);
        InformationSequence informationSequence2 = new InformationSequence();
        informationSequence2.setId(informationSequence1.getId());
        assertThat(informationSequence1).isEqualTo(informationSequence2);
        informationSequence2.setId(2L);
        assertThat(informationSequence1).isNotEqualTo(informationSequence2);
        informationSequence1.setId(null);
        assertThat(informationSequence1).isNotEqualTo(informationSequence2);
    }
}
