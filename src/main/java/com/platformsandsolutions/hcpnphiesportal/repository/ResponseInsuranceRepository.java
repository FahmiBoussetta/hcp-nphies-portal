package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsurance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ResponseInsurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponseInsuranceRepository extends JpaRepository<ResponseInsurance, Long> {}
