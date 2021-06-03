package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiagnosisSequenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnosisSequence.class);
        DiagnosisSequence diagnosisSequence1 = new DiagnosisSequence();
        diagnosisSequence1.setId(1L);
        DiagnosisSequence diagnosisSequence2 = new DiagnosisSequence();
        diagnosisSequence2.setId(diagnosisSequence1.getId());
        assertThat(diagnosisSequence1).isEqualTo(diagnosisSequence2);
        diagnosisSequence2.setId(2L);
        assertThat(diagnosisSequence1).isNotEqualTo(diagnosisSequence2);
        diagnosisSequence1.setId(null);
        assertThat(diagnosisSequence1).isNotEqualTo(diagnosisSequence2);
    }
}
