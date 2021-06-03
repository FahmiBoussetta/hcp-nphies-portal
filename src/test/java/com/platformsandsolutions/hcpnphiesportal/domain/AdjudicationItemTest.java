package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdjudicationItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjudicationItem.class);
        AdjudicationItem adjudicationItem1 = new AdjudicationItem();
        adjudicationItem1.setId(1L);
        AdjudicationItem adjudicationItem2 = new AdjudicationItem();
        adjudicationItem2.setId(adjudicationItem1.getId());
        assertThat(adjudicationItem1).isEqualTo(adjudicationItem2);
        adjudicationItem2.setId(2L);
        assertThat(adjudicationItem1).isNotEqualTo(adjudicationItem2);
        adjudicationItem1.setId(null);
        assertThat(adjudicationItem1).isNotEqualTo(adjudicationItem2);
    }
}
