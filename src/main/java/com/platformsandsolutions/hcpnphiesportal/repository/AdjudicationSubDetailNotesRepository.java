package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailNotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdjudicationSubDetailNotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjudicationSubDetailNotesRepository extends JpaRepository<AdjudicationSubDetailNotes, Long> {}
