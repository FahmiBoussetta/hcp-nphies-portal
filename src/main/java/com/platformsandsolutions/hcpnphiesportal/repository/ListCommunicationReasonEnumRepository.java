package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationReasonEnum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListCommunicationReasonEnum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListCommunicationReasonEnumRepository extends JpaRepository<ListCommunicationReasonEnum, Long> {}
