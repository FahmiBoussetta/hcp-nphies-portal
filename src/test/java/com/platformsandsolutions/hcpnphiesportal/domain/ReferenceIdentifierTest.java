package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReferenceIdentifierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceIdentifier.class);
        ReferenceIdentifier referenceIdentifier1 = new ReferenceIdentifier();
        referenceIdentifier1.setId(1L);
        ReferenceIdentifier referenceIdentifier2 = new ReferenceIdentifier();
        referenceIdentifier2.setId(referenceIdentifier1.getId());
        assertThat(referenceIdentifier1).isEqualTo(referenceIdentifier2);
        referenceIdentifier2.setId(2L);
        assertThat(referenceIdentifier1).isNotEqualTo(referenceIdentifier2);
        referenceIdentifier1.setId(null);
        assertThat(referenceIdentifier1).isNotEqualTo(referenceIdentifier2);
    }
}
