package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubDetailItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubDetailItem.class);
        SubDetailItem subDetailItem1 = new SubDetailItem();
        subDetailItem1.setId(1L);
        SubDetailItem subDetailItem2 = new SubDetailItem();
        subDetailItem2.setId(subDetailItem1.getId());
        assertThat(subDetailItem1).isEqualTo(subDetailItem2);
        subDetailItem2.setId(2L);
        assertThat(subDetailItem1).isNotEqualTo(subDetailItem2);
        subDetailItem1.setId(null);
        assertThat(subDetailItem1).isNotEqualTo(subDetailItem2);
    }
}
