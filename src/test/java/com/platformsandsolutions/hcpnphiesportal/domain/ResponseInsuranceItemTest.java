package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponseInsuranceItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponseInsuranceItem.class);
        ResponseInsuranceItem responseInsuranceItem1 = new ResponseInsuranceItem();
        responseInsuranceItem1.setId(1L);
        ResponseInsuranceItem responseInsuranceItem2 = new ResponseInsuranceItem();
        responseInsuranceItem2.setId(responseInsuranceItem1.getId());
        assertThat(responseInsuranceItem1).isEqualTo(responseInsuranceItem2);
        responseInsuranceItem2.setId(2L);
        assertThat(responseInsuranceItem1).isNotEqualTo(responseInsuranceItem2);
        responseInsuranceItem1.setId(null);
        assertThat(responseInsuranceItem1).isNotEqualTo(responseInsuranceItem2);
    }
}
