package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Givens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Givens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GivensRepository extends JpaRepository<Givens, Long> {}
