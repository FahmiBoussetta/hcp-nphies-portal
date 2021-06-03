package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CRErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRErrorMessages.class);
        CRErrorMessages cRErrorMessages1 = new CRErrorMessages();
        cRErrorMessages1.setId(1L);
        CRErrorMessages cRErrorMessages2 = new CRErrorMessages();
        cRErrorMessages2.setId(cRErrorMessages1.getId());
        assertThat(cRErrorMessages1).isEqualTo(cRErrorMessages2);
        cRErrorMessages2.setId(2L);
        assertThat(cRErrorMessages1).isNotEqualTo(cRErrorMessages2);
        cRErrorMessages1.setId(null);
        assertThat(cRErrorMessages1).isNotEqualTo(cRErrorMessages2);
    }
}
