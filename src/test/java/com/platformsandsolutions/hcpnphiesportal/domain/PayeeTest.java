package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payee.class);
        Payee payee1 = new Payee();
        payee1.setId(1L);
        Payee payee2 = new Payee();
        payee2.setId(payee1.getId());
        assertThat(payee1).isEqualTo(payee2);
        payee2.setId(2L);
        assertThat(payee1).isNotEqualTo(payee2);
        payee1.setId(null);
        assertThat(payee1).isNotEqualTo(payee2);
    }
}
