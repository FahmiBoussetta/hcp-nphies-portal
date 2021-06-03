package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InsuranceBenefitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceBenefit.class);
        InsuranceBenefit insuranceBenefit1 = new InsuranceBenefit();
        insuranceBenefit1.setId(1L);
        InsuranceBenefit insuranceBenefit2 = new InsuranceBenefit();
        insuranceBenefit2.setId(insuranceBenefit1.getId());
        assertThat(insuranceBenefit1).isEqualTo(insuranceBenefit2);
        insuranceBenefit2.setId(2L);
        assertThat(insuranceBenefit1).isNotEqualTo(insuranceBenefit2);
        insuranceBenefit1.setId(null);
        assertThat(insuranceBenefit1).isNotEqualTo(insuranceBenefit2);
    }
}
