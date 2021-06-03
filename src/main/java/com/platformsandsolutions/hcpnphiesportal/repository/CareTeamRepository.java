package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CareTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CareTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareTeamRepository extends JpaRepository<CareTeam, Long> {}
