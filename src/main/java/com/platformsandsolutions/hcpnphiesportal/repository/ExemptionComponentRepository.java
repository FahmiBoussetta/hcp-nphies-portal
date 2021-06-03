package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ExemptionComponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExemptionComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExemptionComponentRepository extends JpaRepository<ExemptionComponent, Long> {}
