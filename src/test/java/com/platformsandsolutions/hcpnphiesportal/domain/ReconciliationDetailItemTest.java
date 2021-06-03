package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReconciliationDetailItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReconciliationDetailItem.class);
        ReconciliationDetailItem reconciliationDetailItem1 = new ReconciliationDetailItem();
        reconciliationDetailItem1.setId(1L);
        ReconciliationDetailItem reconciliationDetailItem2 = new ReconciliationDetailItem();
        reconciliationDetailItem2.setId(reconciliationDetailItem1.getId());
        assertThat(reconciliationDetailItem1).isEqualTo(reconciliationDetailItem2);
        reconciliationDetailItem2.setId(2L);
        assertThat(reconciliationDetailItem1).isNotEqualTo(reconciliationDetailItem2);
        reconciliationDetailItem1.setId(null);
        assertThat(reconciliationDetailItem1).isNotEqualTo(reconciliationDetailItem2);
    }
}
