package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdjudicationNotesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjudicationNotes.class);
        AdjudicationNotes adjudicationNotes1 = new AdjudicationNotes();
        adjudicationNotes1.setId(1L);
        AdjudicationNotes adjudicationNotes2 = new AdjudicationNotes();
        adjudicationNotes2.setId(adjudicationNotes1.getId());
        assertThat(adjudicationNotes1).isEqualTo(adjudicationNotes2);
        adjudicationNotes2.setId(2L);
        assertThat(adjudicationNotes1).isNotEqualTo(adjudicationNotes2);
        adjudicationNotes1.setId(null);
        assertThat(adjudicationNotes1).isNotEqualTo(adjudicationNotes2);
    }
}
