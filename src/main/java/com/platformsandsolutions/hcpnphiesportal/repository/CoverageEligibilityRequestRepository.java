package com.platformsandsolutions.hcpnphiesportal.repository;

import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CoverageEligibilityRequest entity.
 */
@Repository
public interface CoverageEligibilityRequestRepository extends JpaRepository<CoverageEligibilityRequest, Long> {
    @Query(
        value = "select distinct coverageEligibilityRequest from CoverageEligibilityRequest coverageEligibilityRequest left join fetch coverageEligibilityRequest.coverages",
        countQuery = "select count(distinct coverageEligibilityRequest) from CoverageEligibilityRequest coverageEligibilityRequest"
    )
    Page<CoverageEligibilityRequest> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct coverageEligibilityRequest from CoverageEligibilityRequest coverageEligibilityRequest left join fetch coverageEligibilityRequest.coverages"
    )
    List<CoverageEligibilityRequest> findAllWithEagerRelationships();

    @Query(
        "select coverageEligibilityRequest from CoverageEligibilityRequest coverageEligibilityRequest left join fetch coverageEligibilityRequest.coverages where coverageEligibilityRequest.id =:id"
    )
    Optional<CoverageEligibilityRequest> findOneWithEagerRelationships(@Param("id") Long id);
}
