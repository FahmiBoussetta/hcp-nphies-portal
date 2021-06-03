package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Payload;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Payload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayloadRepository extends JpaRepository<Payload, Long> {}
