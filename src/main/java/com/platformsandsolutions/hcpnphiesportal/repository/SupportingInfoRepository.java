package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.SupportingInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SupportingInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupportingInfoRepository extends JpaRepository<SupportingInfo, Long> {}
