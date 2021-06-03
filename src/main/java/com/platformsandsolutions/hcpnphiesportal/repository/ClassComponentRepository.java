package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ClassComponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClassComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassComponentRepository extends JpaRepository<ClassComponent, Long> {}
