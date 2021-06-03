package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.PayNotErrorMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PayNotErrorMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayNotErrorMessagesRepository extends JpaRepository<PayNotErrorMessages, Long> {}
