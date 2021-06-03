package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.PaymentReconciliation;
import com.platformsandsolutions.hcpnphiesportal.repository.PaymentReconciliationRepository;
import com.platformsandsolutions.hcpnphiesportal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.PaymentReconciliation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentReconciliationResource {

    private final Logger log = LoggerFactory.getLogger(PaymentReconciliationResource.class);

    private static final String ENTITY_NAME = "paymentReconciliation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentReconciliationRepository paymentReconciliationRepository;

    public PaymentReconciliationResource(PaymentReconciliationRepository paymentReconciliationRepository) {
        this.paymentReconciliationRepository = paymentReconciliationRepository;
    }

    /**
     * {@code POST  /payment-reconciliations} : Create a new paymentReconciliation.
     *
     * @param paymentReconciliation the paymentReconciliation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentReconciliation, or with status {@code 400 (Bad Request)} if the paymentReconciliation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-reconciliations")
    public ResponseEntity<PaymentReconciliation> createPaymentReconciliation(@RequestBody PaymentReconciliation paymentReconciliation)
        throws URISyntaxException {
        log.debug("REST request to save PaymentReconciliation : {}", paymentReconciliation);
        if (paymentReconciliation.getId() != null) {
            throw new BadRequestAlertException("A new paymentReconciliation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentReconciliation result = paymentReconciliationRepository.save(paymentReconciliation);
        return ResponseEntity
            .created(new URI("/api/payment-reconciliations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-reconciliations/:id} : Updates an existing paymentReconciliation.
     *
     * @param id the id of the paymentReconciliation to save.
     * @param paymentReconciliation the paymentReconciliation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentReconciliation,
     * or with status {@code 400 (Bad Request)} if the paymentReconciliation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentReconciliation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-reconciliations/{id}")
    public ResponseEntity<PaymentReconciliation> updatePaymentReconciliation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentReconciliation paymentReconciliation
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentReconciliation : {}, {}", id, paymentReconciliation);
        if (paymentReconciliation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentReconciliation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentReconciliationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentReconciliation result = paymentReconciliationRepository.save(paymentReconciliation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentReconciliation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-reconciliations/:id} : Partial updates given fields of an existing paymentReconciliation, field will ignore if it is null
     *
     * @param id the id of the paymentReconciliation to save.
     * @param paymentReconciliation the paymentReconciliation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentReconciliation,
     * or with status {@code 400 (Bad Request)} if the paymentReconciliation is not valid,
     * or with status {@code 404 (Not Found)} if the paymentReconciliation is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentReconciliation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-reconciliations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PaymentReconciliation> partialUpdatePaymentReconciliation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentReconciliation paymentReconciliation
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentReconciliation partially : {}, {}", id, paymentReconciliation);
        if (paymentReconciliation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentReconciliation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentReconciliationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentReconciliation> result = paymentReconciliationRepository
            .findById(paymentReconciliation.getId())
            .map(
                existingPaymentReconciliation -> {
                    if (paymentReconciliation.getValue() != null) {
                        existingPaymentReconciliation.setValue(paymentReconciliation.getValue());
                    }
                    if (paymentReconciliation.getSystem() != null) {
                        existingPaymentReconciliation.setSystem(paymentReconciliation.getSystem());
                    }
                    if (paymentReconciliation.getParsed() != null) {
                        existingPaymentReconciliation.setParsed(paymentReconciliation.getParsed());
                    }
                    if (paymentReconciliation.getPeriodStart() != null) {
                        existingPaymentReconciliation.setPeriodStart(paymentReconciliation.getPeriodStart());
                    }
                    if (paymentReconciliation.getPeriodEnd() != null) {
                        existingPaymentReconciliation.setPeriodEnd(paymentReconciliation.getPeriodEnd());
                    }
                    if (paymentReconciliation.getOutcome() != null) {
                        existingPaymentReconciliation.setOutcome(paymentReconciliation.getOutcome());
                    }
                    if (paymentReconciliation.getDisposition() != null) {
                        existingPaymentReconciliation.setDisposition(paymentReconciliation.getDisposition());
                    }
                    if (paymentReconciliation.getPaymentAmount() != null) {
                        existingPaymentReconciliation.setPaymentAmount(paymentReconciliation.getPaymentAmount());
                    }
                    if (paymentReconciliation.getPaymentIdentifier() != null) {
                        existingPaymentReconciliation.setPaymentIdentifier(paymentReconciliation.getPaymentIdentifier());
                    }

                    return existingPaymentReconciliation;
                }
            )
            .map(paymentReconciliationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentReconciliation.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-reconciliations} : get all the paymentReconciliations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentReconciliations in body.
     */
    @GetMapping("/payment-reconciliations")
    public List<PaymentReconciliation> getAllPaymentReconciliations(@RequestParam(required = false) String filter) {
        if ("paymentnotice-is-null".equals(filter)) {
            log.debug("REST request to get all PaymentReconciliations where paymentNotice is null");
            return StreamSupport
                .stream(paymentReconciliationRepository.findAll().spliterator(), false)
                .filter(paymentReconciliation -> paymentReconciliation.getPaymentNotice() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all PaymentReconciliations");
        return paymentReconciliationRepository.findAll();
    }

    /**
     * {@code GET  /payment-reconciliations/:id} : get the "id" paymentReconciliation.
     *
     * @param id the id of the paymentReconciliation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentReconciliation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-reconciliations/{id}")
    public ResponseEntity<PaymentReconciliation> getPaymentReconciliation(@PathVariable Long id) {
        log.debug("REST request to get PaymentReconciliation : {}", id);
        Optional<PaymentReconciliation> paymentReconciliation = paymentReconciliationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentReconciliation);
    }

    /**
     * {@code DELETE  /payment-reconciliations/:id} : delete the "id" paymentReconciliation.
     *
     * @param id the id of the paymentReconciliation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-reconciliations/{id}")
    public ResponseEntity<Void> deletePaymentReconciliation(@PathVariable Long id) {
        log.debug("REST request to delete PaymentReconciliation : {}", id);
        paymentReconciliationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
