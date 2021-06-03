package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CovEliErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CovEliErrorMessages.class);
        CovEliErrorMessages covEliErrorMessages1 = new CovEliErrorMessages();
        covEliErrorMessages1.setId(1L);
        CovEliErrorMessages covEliErrorMessages2 = new CovEliErrorMessages();
        covEliErrorMessages2.setId(covEliErrorMessages1.getId());
        assertThat(covEliErrorMessages1).isEqualTo(covEliErrorMessages2);
        covEliErrorMessages2.setId(2L);
        assertThat(covEliErrorMessages1).isNotEqualTo(covEliErrorMessages2);
        covEliErrorMessages1.setId(null);
        assertThat(covEliErrorMessages1).isNotEqualTo(covEliErrorMessages2);
    }
}
