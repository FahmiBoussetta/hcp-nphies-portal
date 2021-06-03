package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoverageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coverage.class);
        Coverage coverage1 = new Coverage();
        coverage1.setId(1L);
        Coverage coverage2 = new Coverage();
        coverage2.setId(coverage1.getId());
        assertThat(coverage1).isEqualTo(coverage2);
        coverage2.setId(2L);
        assertThat(coverage1).isNotEqualTo(coverage2);
        coverage1.setId(null);
        assertThat(coverage1).isNotEqualTo(coverage2);
    }
}
