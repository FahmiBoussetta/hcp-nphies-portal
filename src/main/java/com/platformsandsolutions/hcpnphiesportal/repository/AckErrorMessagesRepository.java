package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.AckErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AckErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AckErrorMessagesRepository extends JpaRepository<AckErrorMessages, Long> {}
