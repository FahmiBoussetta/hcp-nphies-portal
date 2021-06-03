package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CostToBeneficiaryComponent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CostToBeneficiaryComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostToBeneficiaryComponentRepository extends JpaRepository<CostToBeneficiaryComponent, Long> {}
