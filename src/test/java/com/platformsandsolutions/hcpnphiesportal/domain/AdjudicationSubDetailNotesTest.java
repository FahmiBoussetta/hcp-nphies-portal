package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdjudicationSubDetailNotesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjudicationSubDetailNotes.class);
        AdjudicationSubDetailNotes adjudicationSubDetailNotes1 = new AdjudicationSubDetailNotes();
        adjudicationSubDetailNotes1.setId(1L);
        AdjudicationSubDetailNotes adjudicationSubDetailNotes2 = new AdjudicationSubDetailNotes();
        adjudicationSubDetailNotes2.setId(adjudicationSubDetailNotes1.getId());
        assertThat(adjudicationSubDetailNotes1).isEqualTo(adjudicationSubDetailNotes2);
        adjudicationSubDetailNotes2.setId(2L);
        assertThat(adjudicationSubDetailNotes1).isNotEqualTo(adjudicationSubDetailNotes2);
        adjudicationSubDetailNotes1.setId(null);
        assertThat(adjudicationSubDetailNotes1).isNotEqualTo(adjudicationSubDetailNotes2);
    }
}
