package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskInputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskInput.class);
        TaskInput taskInput1 = new TaskInput();
        taskInput1.setId(1L);
        TaskInput taskInput2 = new TaskInput();
        taskInput2.setId(taskInput1.getId());
        assertThat(taskInput1).isEqualTo(taskInput2);
        taskInput2.setId(2L);
        assertThat(taskInput1).isNotEqualTo(taskInput2);
        taskInput1.setId(null);
        assertThat(taskInput1).isNotEqualTo(taskInput2);
    }
}
