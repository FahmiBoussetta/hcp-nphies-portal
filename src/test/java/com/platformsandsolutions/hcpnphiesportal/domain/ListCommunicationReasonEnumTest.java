package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListCommunicationReasonEnumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCommunicationReasonEnum.class);
        ListCommunicationReasonEnum listCommunicationReasonEnum1 = new ListCommunicationReasonEnum();
        listCommunicationReasonEnum1.setId(1L);
        ListCommunicationReasonEnum listCommunicationReasonEnum2 = new ListCommunicationReasonEnum();
        listCommunicationReasonEnum2.setId(listCommunicationReasonEnum1.getId());
        assertThat(listCommunicationReasonEnum1).isEqualTo(listCommunicationReasonEnum2);
        listCommunicationReasonEnum2.setId(2L);
        assertThat(listCommunicationReasonEnum1).isNotEqualTo(listCommunicationReasonEnum2);
        listCommunicationReasonEnum1.setId(null);
        assertThat(listCommunicationReasonEnum1).isNotEqualTo(listCommunicationReasonEnum2);
    }
}
