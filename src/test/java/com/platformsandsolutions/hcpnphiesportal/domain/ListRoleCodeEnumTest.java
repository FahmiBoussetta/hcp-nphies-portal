package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListRoleCodeEnumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListRoleCodeEnum.class);
        ListRoleCodeEnum listRoleCodeEnum1 = new ListRoleCodeEnum();
        listRoleCodeEnum1.setId(1L);
        ListRoleCodeEnum listRoleCodeEnum2 = new ListRoleCodeEnum();
        listRoleCodeEnum2.setId(listRoleCodeEnum1.getId());
        assertThat(listRoleCodeEnum1).isEqualTo(listRoleCodeEnum2);
        listRoleCodeEnum2.setId(2L);
        assertThat(listRoleCodeEnum1).isNotEqualTo(listRoleCodeEnum2);
        listRoleCodeEnum1.setId(null);
        assertThat(listRoleCodeEnum1).isNotEqualTo(listRoleCodeEnum2);
    }
}
