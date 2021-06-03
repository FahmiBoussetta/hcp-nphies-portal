package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ReferenceIdentifier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ReferenceIdentifier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferenceIdentifierRepository extends JpaRepository<ReferenceIdentifier, Long> {}
