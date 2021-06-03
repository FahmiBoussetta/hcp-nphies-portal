package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Diagnosis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Diagnosis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {}
