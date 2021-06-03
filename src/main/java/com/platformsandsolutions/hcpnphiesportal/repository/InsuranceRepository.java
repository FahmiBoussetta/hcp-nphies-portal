package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Insurance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Insurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {}
