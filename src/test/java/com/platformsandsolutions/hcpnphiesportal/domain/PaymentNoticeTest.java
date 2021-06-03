package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentNoticeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentNotice.class);
        PaymentNotice paymentNotice1 = new PaymentNotice();
        paymentNotice1.setId(1L);
        PaymentNotice paymentNotice2 = new PaymentNotice();
        paymentNotice2.setId(paymentNotice1.getId());
        assertThat(paymentNotice1).isEqualTo(paymentNotice2);
        paymentNotice2.setId(2L);
        assertThat(paymentNotice1).isNotEqualTo(paymentNotice2);
        paymentNotice1.setId(null);
        assertThat(paymentNotice1).isNotEqualTo(paymentNotice2);
    }
}
