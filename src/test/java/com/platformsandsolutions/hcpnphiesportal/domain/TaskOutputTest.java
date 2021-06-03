package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskOutputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskOutput.class);
        TaskOutput taskOutput1 = new TaskOutput();
        taskOutput1.setId(1L);
        TaskOutput taskOutput2 = new TaskOutput();
        taskOutput2.setId(taskOutput1.getId());
        assertThat(taskOutput1).isEqualTo(taskOutput2);
        taskOutput2.setId(2L);
        assertThat(taskOutput1).isNotEqualTo(taskOutput2);
        taskOutput1.setId(null);
        assertThat(taskOutput1).isNotEqualTo(taskOutput2);
    }
}
