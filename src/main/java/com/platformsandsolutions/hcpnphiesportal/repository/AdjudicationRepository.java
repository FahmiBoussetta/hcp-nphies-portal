package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.Adjudication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Adjudication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjudicationRepository extends JpaRepository<Adjudication, Long> {}
