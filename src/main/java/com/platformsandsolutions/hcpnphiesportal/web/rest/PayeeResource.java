package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Payee;
import com.platformsandsolutions.hcpnphiesportal.repository.PayeeRepository;
import com.platformsandsolutions.hcpnphiesportal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Payee}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PayeeResource {

    private final Logger log = LoggerFactory.getLogger(PayeeResource.class);

    private static final String ENTITY_NAME = "payee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayeeRepository payeeRepository;

    public PayeeResource(PayeeRepository payeeRepository) {
        this.payeeRepository = payeeRepository;
    }

    /**
     * {@code POST  /payees} : Create a new payee.
     *
     * @param payee the payee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payee, or with status {@code 400 (Bad Request)} if the payee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payees")
    public ResponseEntity<Payee> createPayee(@Valid @RequestBody Payee payee) throws URISyntaxException {
        log.debug("REST request to save Payee : {}", payee);
        if (payee.getId() != null) {
            throw new BadRequestAlertException("A new payee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payee result = payeeRepository.save(payee);
        return ResponseEntity
            .created(new URI("/api/payees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payees/:id} : Updates an existing payee.
     *
     * @param id the id of the payee to save.
     * @param payee the payee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payee,
     * or with status {@code 400 (Bad Request)} if the payee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payees/{id}")
    public ResponseEntity<Payee> updatePayee(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Payee payee)
        throws URISyntaxException {
        log.debug("REST request to update Payee : {}, {}", id, payee);
        if (payee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Payee result = payeeRepository.save(payee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payees/:id} : Partial updates given fields of an existing payee, field will ignore if it is null
     *
     * @param id the id of the payee to save.
     * @param payee the payee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payee,
     * or with status {@code 400 (Bad Request)} if the payee is not valid,
     * or with status {@code 404 (Not Found)} if the payee is not found,
     * or with status {@code 500 (Internal Server Error)} if the payee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payees/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Payee> partialUpdatePayee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Payee payee
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payee partially : {}, {}", id, payee);
        if (payee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Payee> result = payeeRepository
            .findById(payee.getId())
            .map(
                existingPayee -> {
                    if (payee.getType() != null) {
                        existingPayee.setType(payee.getType());
                    }

                    return existingPayee;
                }
            )
            .map(payeeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payee.getId().toString())
        );
    }

    /**
     * {@code GET  /payees} : get all the payees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payees in body.
     */
    @GetMapping("/payees")
    public List<Payee> getAllPayees() {
        log.debug("REST request to get all Payees");
        return payeeRepository.findAll();
    }

    /**
     * {@code GET  /payees/:id} : get the "id" payee.
     *
     * @param id the id of the payee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payees/{id}")
    public ResponseEntity<Payee> getPayee(@PathVariable Long id) {
        log.debug("REST request to get Payee : {}", id);
        Optional<Payee> payee = payeeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payee);
    }

    /**
     * {@code DELETE  /payees/:id} : delete the "id" payee.
     *
     * @param id the id of the payee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payees/{id}")
    public ResponseEntity<Void> deletePayee(@PathVariable Long id) {
        log.debug("REST request to delete Payee : {}", id);
        payeeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
