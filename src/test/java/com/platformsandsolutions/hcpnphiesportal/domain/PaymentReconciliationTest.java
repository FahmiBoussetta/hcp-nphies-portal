package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentReconciliationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentReconciliation.class);
        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setId(1L);
        PaymentReconciliation paymentReconciliation2 = new PaymentReconciliation();
        paymentReconciliation2.setId(paymentReconciliation1.getId());
        assertThat(paymentReconciliation1).isEqualTo(paymentReconciliation2);
        paymentReconciliation2.setId(2L);
        assertThat(paymentReconciliation1).isNotEqualTo(paymentReconciliation2);
        paymentReconciliation1.setId(null);
        assertThat(paymentReconciliation1).isNotEqualTo(paymentReconciliation2);
    }
}
