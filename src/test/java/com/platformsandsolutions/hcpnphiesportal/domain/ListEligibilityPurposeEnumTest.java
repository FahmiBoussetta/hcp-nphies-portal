package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListEligibilityPurposeEnumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListEligibilityPurposeEnum.class);
        ListEligibilityPurposeEnum listEligibilityPurposeEnum1 = new ListEligibilityPurposeEnum();
        listEligibilityPurposeEnum1.setId(1L);
        ListEligibilityPurposeEnum listEligibilityPurposeEnum2 = new ListEligibilityPurposeEnum();
        listEligibilityPurposeEnum2.setId(listEligibilityPurposeEnum1.getId());
        assertThat(listEligibilityPurposeEnum1).isEqualTo(listEligibilityPurposeEnum2);
        listEligibilityPurposeEnum2.setId(2L);
        assertThat(listEligibilityPurposeEnum1).isNotEqualTo(listEligibilityPurposeEnum2);
        listEligibilityPurposeEnum1.setId(null);
        assertThat(listEligibilityPurposeEnum1).isNotEqualTo(listEligibilityPurposeEnum2);
    }
}
