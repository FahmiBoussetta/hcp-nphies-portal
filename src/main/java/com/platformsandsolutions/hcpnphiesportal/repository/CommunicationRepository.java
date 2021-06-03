package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Communication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Communication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {}
