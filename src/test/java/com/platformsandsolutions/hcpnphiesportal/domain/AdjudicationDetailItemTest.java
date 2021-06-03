package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdjudicationDetailItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjudicationDetailItem.class);
        AdjudicationDetailItem adjudicationDetailItem1 = new AdjudicationDetailItem();
        adjudicationDetailItem1.setId(1L);
        AdjudicationDetailItem adjudicationDetailItem2 = new AdjudicationDetailItem();
        adjudicationDetailItem2.setId(adjudicationDetailItem1.getId());
        assertThat(adjudicationDetailItem1).isEqualTo(adjudicationDetailItem2);
        adjudicationDetailItem2.setId(2L);
        assertThat(adjudicationDetailItem1).isNotEqualTo(adjudicationDetailItem2);
        adjudicationDetailItem1.setId(null);
        assertThat(adjudicationDetailItem1).isNotEqualTo(adjudicationDetailItem2);
    }
}
