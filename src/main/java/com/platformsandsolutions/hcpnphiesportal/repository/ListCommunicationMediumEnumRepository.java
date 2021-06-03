package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationMediumEnum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListCommunicationMediumEnum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListCommunicationMediumEnumRepository extends JpaRepository<ListCommunicationMediumEnum, Long> {}
