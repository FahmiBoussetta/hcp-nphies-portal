package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailNotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdjudicationDetailNotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjudicationDetailNotesRepository extends JpaRepository<AdjudicationDetailNotes, Long> {}
