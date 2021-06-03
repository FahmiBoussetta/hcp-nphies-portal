package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdjudicationDetailNotesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjudicationDetailNotes.class);
        AdjudicationDetailNotes adjudicationDetailNotes1 = new AdjudicationDetailNotes();
        adjudicationDetailNotes1.setId(1L);
        AdjudicationDetailNotes adjudicationDetailNotes2 = new AdjudicationDetailNotes();
        adjudicationDetailNotes2.setId(adjudicationDetailNotes1.getId());
        assertThat(adjudicationDetailNotes1).isEqualTo(adjudicationDetailNotes2);
        adjudicationDetailNotes2.setId(2L);
        assertThat(adjudicationDetailNotes1).isNotEqualTo(adjudicationDetailNotes2);
        adjudicationDetailNotes1.setId(null);
        assertThat(adjudicationDetailNotes1).isNotEqualTo(adjudicationDetailNotes2);
    }
}
