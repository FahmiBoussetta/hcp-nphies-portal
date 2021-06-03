package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponseInsuranceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponseInsurance.class);
        ResponseInsurance responseInsurance1 = new ResponseInsurance();
        responseInsurance1.setId(1L);
        ResponseInsurance responseInsurance2 = new ResponseInsurance();
        responseInsurance2.setId(responseInsurance1.getId());
        assertThat(responseInsurance1).isEqualTo(responseInsurance2);
        responseInsurance2.setId(2L);
        assertThat(responseInsurance1).isNotEqualTo(responseInsurance2);
        responseInsurance1.setId(null);
        assertThat(responseInsurance1).isNotEqualTo(responseInsurance2);
    }
}
