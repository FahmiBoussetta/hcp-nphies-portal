package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsuranceItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResponseInsuranceItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponseInsuranceItemRepository extends JpaRepository<ResponseInsuranceItem, Long> {}
