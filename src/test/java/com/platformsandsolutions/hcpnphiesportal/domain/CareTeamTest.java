package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CareTeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareTeam.class);
        CareTeam careTeam1 = new CareTeam();
        careTeam1.setId(1L);
        CareTeam careTeam2 = new CareTeam();
        careTeam2.setId(careTeam1.getId());
        assertThat(careTeam1).isEqualTo(careTeam2);
        careTeam2.setId(2L);
        assertThat(careTeam1).isNotEqualTo(careTeam2);
        careTeam1.setId(null);
        assertThat(careTeam1).isNotEqualTo(careTeam2);
    }
}
