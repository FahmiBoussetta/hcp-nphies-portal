package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quantity.class);
        Quantity quantity1 = new Quantity();
        quantity1.setId(1L);
        Quantity quantity2 = new Quantity();
        quantity2.setId(quantity1.getId());
        assertThat(quantity1).isEqualTo(quantity2);
        quantity2.setId(2L);
        assertThat(quantity1).isNotEqualTo(quantity2);
        quantity1.setId(null);
        assertThat(quantity1).isNotEqualTo(quantity2);
    }
}
