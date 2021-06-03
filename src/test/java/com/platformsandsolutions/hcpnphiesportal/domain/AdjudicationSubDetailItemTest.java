package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdjudicationSubDetailItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjudicationSubDetailItem.class);
        AdjudicationSubDetailItem adjudicationSubDetailItem1 = new AdjudicationSubDetailItem();
        adjudicationSubDetailItem1.setId(1L);
        AdjudicationSubDetailItem adjudicationSubDetailItem2 = new AdjudicationSubDetailItem();
        adjudicationSubDetailItem2.setId(adjudicationSubDetailItem1.getId());
        assertThat(adjudicationSubDetailItem1).isEqualTo(adjudicationSubDetailItem2);
        adjudicationSubDetailItem2.setId(2L);
        assertThat(adjudicationSubDetailItem1).isNotEqualTo(adjudicationSubDetailItem2);
        adjudicationSubDetailItem1.setId(null);
        assertThat(adjudicationSubDetailItem1).isNotEqualTo(adjudicationSubDetailItem2);
    }
}
