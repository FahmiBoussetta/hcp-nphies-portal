package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CoverageEligibilityRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoverageEligibilityRequestRepository extends JpaRepository<CoverageEligibilityRequest, Long> {}
