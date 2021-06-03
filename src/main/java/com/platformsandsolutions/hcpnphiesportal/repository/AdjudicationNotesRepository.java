package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationNotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdjudicationNotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjudicationNotesRepository extends JpaRepository<AdjudicationNotes, Long> {}
