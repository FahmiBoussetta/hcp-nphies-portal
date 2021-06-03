package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdjudicationItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdjudicationItemRepository extends JpaRepository<AdjudicationItem, Long> {}
