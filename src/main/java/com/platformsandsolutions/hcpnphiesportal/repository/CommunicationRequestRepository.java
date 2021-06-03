package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CommunicationRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CommunicationRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationRequestRepository extends JpaRepository<CommunicationRequest, Long> {}
