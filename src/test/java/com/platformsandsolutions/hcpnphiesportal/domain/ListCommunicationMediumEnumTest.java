package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListCommunicationMediumEnumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCommunicationMediumEnum.class);
        ListCommunicationMediumEnum listCommunicationMediumEnum1 = new ListCommunicationMediumEnum();
        listCommunicationMediumEnum1.setId(1L);
        ListCommunicationMediumEnum listCommunicationMediumEnum2 = new ListCommunicationMediumEnum();
        listCommunicationMediumEnum2.setId(listCommunicationMediumEnum1.getId());
        assertThat(listCommunicationMediumEnum1).isEqualTo(listCommunicationMediumEnum2);
        listCommunicationMediumEnum2.setId(2L);
        assertThat(listCommunicationMediumEnum1).isNotEqualTo(listCommunicationMediumEnum2);
        listCommunicationMediumEnum1.setId(null);
        assertThat(listCommunicationMediumEnum1).isNotEqualTo(listCommunicationMediumEnum2);
    }
}
