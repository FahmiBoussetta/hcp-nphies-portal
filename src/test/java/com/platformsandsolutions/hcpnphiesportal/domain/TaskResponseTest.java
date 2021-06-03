package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskResponseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskResponse.class);
        TaskResponse taskResponse1 = new TaskResponse();
        taskResponse1.setId(1L);
        TaskResponse taskResponse2 = new TaskResponse();
        taskResponse2.setId(taskResponse1.getId());
        assertThat(taskResponse1).isEqualTo(taskResponse2);
        taskResponse2.setId(2L);
        assertThat(taskResponse1).isNotEqualTo(taskResponse2);
        taskResponse1.setId(null);
        assertThat(taskResponse1).isNotEqualTo(taskResponse2);
    }
}
