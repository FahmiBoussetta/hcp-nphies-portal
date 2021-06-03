package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailItem.class);
        DetailItem detailItem1 = new DetailItem();
        detailItem1.setId(1L);
        DetailItem detailItem2 = new DetailItem();
        detailItem2.setId(detailItem1.getId());
        assertThat(detailItem1).isEqualTo(detailItem2);
        detailItem2.setId(2L);
        assertThat(detailItem1).isNotEqualTo(detailItem2);
        detailItem1.setId(null);
        assertThat(detailItem1).isNotEqualTo(detailItem2);
    }
}
