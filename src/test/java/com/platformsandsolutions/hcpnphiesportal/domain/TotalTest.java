package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TotalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Total.class);
        Total total1 = new Total();
        total1.setId(1L);
        Total total2 = new Total();
        total2.setId(total1.getId());
        assertThat(total1).isEqualTo(total2);
        total2.setId(2L);
        assertThat(total1).isNotEqualTo(total2);
        total1.setId(null);
        assertThat(total1).isNotEqualTo(total2);
    }
}
