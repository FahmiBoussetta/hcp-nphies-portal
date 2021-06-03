package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CoverageEligibilityResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoverageEligibilityResponseRepository extends JpaRepository<CoverageEligibilityResponse, Long> {}
