package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ListSpecialtyEnum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListSpecialtyEnum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListSpecialtyEnumRepository extends JpaRepository<ListSpecialtyEnum, Long> {}
