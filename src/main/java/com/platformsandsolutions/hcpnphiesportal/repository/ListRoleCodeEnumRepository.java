package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.ListRoleCodeEnum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ListRoleCodeEnum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListRoleCodeEnumRepository extends JpaRepository<ListRoleCodeEnum, Long> {}
