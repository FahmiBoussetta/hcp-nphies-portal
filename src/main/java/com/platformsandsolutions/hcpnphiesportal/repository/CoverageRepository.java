package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Coverage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Coverage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoverageRepository extends JpaRepository<Coverage, Long> {}
