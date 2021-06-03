package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpeOutErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpeOutErrorMessages.class);
        OpeOutErrorMessages opeOutErrorMessages1 = new OpeOutErrorMessages();
        opeOutErrorMessages1.setId(1L);
        OpeOutErrorMessages opeOutErrorMessages2 = new OpeOutErrorMessages();
        opeOutErrorMessages2.setId(opeOutErrorMessages1.getId());
        assertThat(opeOutErrorMessages1).isEqualTo(opeOutErrorMessages2);
        opeOutErrorMessages2.setId(2L);
        assertThat(opeOutErrorMessages1).isNotEqualTo(opeOutErrorMessages2);
        opeOutErrorMessages1.setId(null);
        assertThat(opeOutErrorMessages1).isNotEqualTo(opeOutErrorMessages2);
    }
}
