package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.InformationSequence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InformationSequence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformationSequenceRepository extends JpaRepository<InformationSequence, Long> {}
