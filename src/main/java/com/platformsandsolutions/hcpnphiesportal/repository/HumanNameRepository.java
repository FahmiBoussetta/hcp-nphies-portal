package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.HumanName;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HumanName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HumanNameRepository extends JpaRepository<HumanName, Long> {}
