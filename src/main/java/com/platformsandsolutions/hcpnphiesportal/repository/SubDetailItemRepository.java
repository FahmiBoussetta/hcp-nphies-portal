package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.SubDetailItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubDetailItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubDetailItemRepository extends JpaRepository<SubDetailItem, Long> {}
