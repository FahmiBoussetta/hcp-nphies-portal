package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComErrorMessages.class);
        ComErrorMessages comErrorMessages1 = new ComErrorMessages();
        comErrorMessages1.setId(1L);
        ComErrorMessages comErrorMessages2 = new ComErrorMessages();
        comErrorMessages2.setId(comErrorMessages1.getId());
        assertThat(comErrorMessages1).isEqualTo(comErrorMessages2);
        comErrorMessages2.setId(2L);
        assertThat(comErrorMessages1).isNotEqualTo(comErrorMessages2);
        comErrorMessages1.setId(null);
        assertThat(comErrorMessages1).isNotEqualTo(comErrorMessages2);
    }
}
