package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Related.class);
        Related related1 = new Related();
        related1.setId(1L);
        Related related2 = new Related();
        related2.setId(related1.getId());
        assertThat(related1).isEqualTo(related2);
        related2.setId(2L);
        assertThat(related1).isNotEqualTo(related2);
        related1.setId(null);
        assertThat(related1).isNotEqualTo(related2);
    }
}
