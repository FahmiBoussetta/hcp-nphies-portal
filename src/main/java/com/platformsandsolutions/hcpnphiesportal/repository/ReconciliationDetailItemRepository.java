package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ReconciliationDetailItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReconciliationDetailItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReconciliationDetailItemRepository extends JpaRepository<ReconciliationDetailItem, Long> {}
