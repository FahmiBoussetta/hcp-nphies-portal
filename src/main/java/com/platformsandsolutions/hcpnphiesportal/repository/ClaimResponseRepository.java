package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ClaimResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClaimResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimResponseRepository extends JpaRepository<ClaimResponse, Long> {}
