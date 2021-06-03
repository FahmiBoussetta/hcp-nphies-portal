package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CostToBeneficiaryComponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostToBeneficiaryComponent.class);
        CostToBeneficiaryComponent costToBeneficiaryComponent1 = new CostToBeneficiaryComponent();
        costToBeneficiaryComponent1.setId(1L);
        CostToBeneficiaryComponent costToBeneficiaryComponent2 = new CostToBeneficiaryComponent();
        costToBeneficiaryComponent2.setId(costToBeneficiaryComponent1.getId());
        assertThat(costToBeneficiaryComponent1).isEqualTo(costToBeneficiaryComponent2);
        costToBeneficiaryComponent2.setId(2L);
        assertThat(costToBeneficiaryComponent1).isNotEqualTo(costToBeneficiaryComponent2);
        costToBeneficiaryComponent1.setId(null);
        assertThat(costToBeneficiaryComponent1).isNotEqualTo(costToBeneficiaryComponent2);
    }
}
