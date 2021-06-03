package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CovEliRespErrorMessagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CovEliRespErrorMessages.class);
        CovEliRespErrorMessages covEliRespErrorMessages1 = new CovEliRespErrorMessages();
        covEliRespErrorMessages1.setId(1L);
        CovEliRespErrorMessages covEliRespErrorMessages2 = new CovEliRespErrorMessages();
        covEliRespErrorMessages2.setId(covEliRespErrorMessages1.getId());
        assertThat(covEliRespErrorMessages1).isEqualTo(covEliRespErrorMessages2);
        covEliRespErrorMessages2.setId(2L);
        assertThat(covEliRespErrorMessages1).isNotEqualTo(covEliRespErrorMessages2);
        covEliRespErrorMessages1.setId(null);
        assertThat(covEliRespErrorMessages1).isNotEqualTo(covEliRespErrorMessages2);
    }
}
