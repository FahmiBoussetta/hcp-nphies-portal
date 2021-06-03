package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PractitionerRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PractitionerRole.class);
        PractitionerRole practitionerRole1 = new PractitionerRole();
        practitionerRole1.setId(1L);
        PractitionerRole practitionerRole2 = new PractitionerRole();
        practitionerRole2.setId(practitionerRole1.getId());
        assertThat(practitionerRole1).isEqualTo(practitionerRole2);
        practitionerRole2.setId(2L);
        assertThat(practitionerRole1).isNotEqualTo(practitionerRole2);
        practitionerRole1.setId(null);
        assertThat(practitionerRole1).isNotEqualTo(practitionerRole2);
    }
}
