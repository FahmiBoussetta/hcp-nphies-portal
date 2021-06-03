package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.OpeOutErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OpeOutErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpeOutErrorMessagesRepository extends JpaRepository<OpeOutErrorMessages, Long> {}
