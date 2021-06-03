package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.DiagnosisSequence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DiagnosisSequence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosisSequenceRepository extends JpaRepository<DiagnosisSequence, Long> {}
