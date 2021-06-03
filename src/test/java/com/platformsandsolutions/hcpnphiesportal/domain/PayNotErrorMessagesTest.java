package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayNotErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayNotErrorMessages.class);
        PayNotErrorMessages payNotErrorMessages1 = new PayNotErrorMessages();
        payNotErrorMessages1.setId(1L);
        PayNotErrorMessages payNotErrorMessages2 = new PayNotErrorMessages();
        payNotErrorMessages2.setId(payNotErrorMessages1.getId());
        assertThat(payNotErrorMessages1).isEqualTo(payNotErrorMessages2);
        payNotErrorMessages2.setId(2L);
        assertThat(payNotErrorMessages1).isNotEqualTo(payNotErrorMessages2);
        payNotErrorMessages1.setId(null);
        assertThat(payNotErrorMessages1).isNotEqualTo(payNotErrorMessages2);
    }
}
