package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.OperationOutcome;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OperationOutcome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationOutcomeRepository extends JpaRepository<OperationOutcome, Long> {}
