package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ComErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComErrorMessagesRepository extends JpaRepository<ComErrorMessages, Long> {}
