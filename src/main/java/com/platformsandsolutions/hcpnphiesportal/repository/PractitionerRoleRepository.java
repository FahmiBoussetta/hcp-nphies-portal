package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.PractitionerRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PractitionerRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PractitionerRoleRepository extends JpaRepository<PractitionerRole, Long> {}
