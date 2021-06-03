package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.PaymentReconciliation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PaymentReconciliation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentReconciliationRepository extends JpaRepository<PaymentReconciliation, Long> {}
