package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Acknowledgement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Acknowledgement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcknowledgementRepository extends JpaRepository<Acknowledgement, Long> {}
