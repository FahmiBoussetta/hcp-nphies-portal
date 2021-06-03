package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListSpecialtyEnumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListSpecialtyEnum.class);
        ListSpecialtyEnum listSpecialtyEnum1 = new ListSpecialtyEnum();
        listSpecialtyEnum1.setId(1L);
        ListSpecialtyEnum listSpecialtyEnum2 = new ListSpecialtyEnum();
        listSpecialtyEnum2.setId(listSpecialtyEnum1.getId());
        assertThat(listSpecialtyEnum1).isEqualTo(listSpecialtyEnum2);
        listSpecialtyEnum2.setId(2L);
        assertThat(listSpecialtyEnum1).isNotEqualTo(listSpecialtyEnum2);
        listSpecialtyEnum1.setId(null);
        assertThat(listSpecialtyEnum1).isNotEqualTo(listSpecialtyEnum2);
    }
}
