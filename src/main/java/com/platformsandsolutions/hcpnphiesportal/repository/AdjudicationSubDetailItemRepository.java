package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdjudicationSubDetailItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjudicationSubDetailItemRepository extends JpaRepository<AdjudicationSubDetailItem, Long> {}
