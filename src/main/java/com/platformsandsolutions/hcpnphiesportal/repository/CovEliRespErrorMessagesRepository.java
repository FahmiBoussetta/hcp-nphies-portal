package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CovEliRespErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CovEliRespErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CovEliRespErrorMessagesRepository extends JpaRepository<CovEliRespErrorMessages, Long> {}
