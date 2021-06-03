package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Accident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Accident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long> {}
