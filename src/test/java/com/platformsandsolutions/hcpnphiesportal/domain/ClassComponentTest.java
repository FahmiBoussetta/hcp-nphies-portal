package com.platformsandsolutions.hcpnphiesportal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassComponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassComponent.class);
        ClassComponent classComponent1 = new ClassComponent();
        classComponent1.setId(1L);
        ClassComponent classComponent2 = new ClassComponent();
        classComponent2.setId(classComponent1.getId());
        assertThat(classComponent1).isEqualTo(classComponent2);
        classComponent2.setId(2L);
        assertThat(classComponent1).isNotEqualTo(classComponent2);
        classComponent1.setId(null);
        assertThat(classComponent1).isNotEqualTo(classComponent2);
    }
}
