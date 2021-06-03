package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GivensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Givens.class);
        Givens givens1 = new Givens();
        givens1.setId(1L);
        Givens givens2 = new Givens();
        givens2.setId(givens1.getId());
        assertThat(givens1).isEqualTo(givens2);
        givens2.setId(2L);
        assertThat(givens1).isNotEqualTo(givens2);
        givens1.setId(null);
        assertThat(givens1).isNotEqualTo(givens2);
    }
}
