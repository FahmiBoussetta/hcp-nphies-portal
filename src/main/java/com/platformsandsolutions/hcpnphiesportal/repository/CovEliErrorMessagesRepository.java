package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CovEliErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CovEliErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CovEliErrorMessagesRepository extends JpaRepository<CovEliErrorMessages, Long> {}
