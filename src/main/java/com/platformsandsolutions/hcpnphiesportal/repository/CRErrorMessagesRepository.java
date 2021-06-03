package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CRErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CRErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CRErrorMessagesRepository extends JpaRepository<CRErrorMessages, Long> {}
