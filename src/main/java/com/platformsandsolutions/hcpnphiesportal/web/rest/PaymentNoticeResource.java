package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.PaymentNotice;
import com.platformsandsolutions.hcpnphiesportal.repository.PaymentNoticeRepository;
import com.platformsandsolutions.hcpnphiesportal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.PaymentNotice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentNoticeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentNoticeResource.class);

    private static final String ENTITY_NAME = "paymentNotice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentNoticeRepository paymentNoticeRepository;

    public PaymentNoticeResource(PaymentNoticeRepository paymentNoticeRepository) {
        this.paymentNoticeRepository = paymentNoticeRepository;
    }

    /**
     * {@code POST  /payment-notices} : Create a new paymentNotice.
     *
     * @param paymentNotice the paymentNotice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentNotice, or with status {@code 400 (Bad Request)} if the paymentNotice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-notices")
    public ResponseEntity<PaymentNotice> createPaymentNotice(@RequestBody PaymentNotice paymentNotice) throws URISyntaxException {
        log.debug("REST request to save PaymentNotice : {}", paymentNotice);
        if (paymentNotice.getId() != null) {
            throw new BadRequestAlertException("A new paymentNotice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentNotice result = paymentNoticeRepository.save(paymentNotice);
        return ResponseEntity
            .created(new URI("/api/payment-notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-notices/:id} : Updates an existing paymentNotice.
     *
     * @param id the id of the paymentNotice to save.
     * @param paymentNotice the paymentNotice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentNotice,
     * or with status {@code 400 (Bad Request)} if the paymentNotice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentNotice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-notices/{id}")
    public ResponseEntity<PaymentNotice> updatePaymentNotice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentNotice paymentNotice
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentNotice : {}, {}", id, paymentNotice);
        if (paymentNotice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentNotice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentNoticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentNotice result = paymentNoticeRepository.save(paymentNotice);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentNotice.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-notices/:id} : Partial updates given fields of an existing paymentNotice, field will ignore if it is null
     *
     * @param id the id of the paymentNotice to save.
     * @param paymentNotice the paymentNotice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentNotice,
     * or with status {@code 400 (Bad Request)} if the paymentNotice is not valid,
     * or with status {@code 404 (Not Found)} if the paymentNotice is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentNotice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-notices/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PaymentNotice> partialUpdatePaymentNotice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentNotice paymentNotice
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentNotice partially : {}, {}", id, paymentNotice);
        if (paymentNotice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentNotice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentNoticeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentNotice> result = paymentNoticeRepository
            .findById(paymentNotice.getId())
            .map(
                existingPaymentNotice -> {
                    if (paymentNotice.getGuid() != null) {
                        existingPaymentNotice.setGuid(paymentNotice.getGuid());
                    }
                    if (paymentNotice.getParsed() != null) {
                        existingPaymentNotice.setParsed(paymentNotice.getParsed());
                    }
                    if (paymentNotice.getIdentifier() != null) {
                        existingPaymentNotice.setIdentifier(paymentNotice.getIdentifier());
                    }
                    if (paymentNotice.getPaymentDate() != null) {
                        existingPaymentNotice.setPaymentDate(paymentNotice.getPaymentDate());
                    }
                    if (paymentNotice.getAmount() != null) {
                        existingPaymentNotice.setAmount(paymentNotice.getAmount());
                    }
                    if (paymentNotice.getPaymentStatus() != null) {
                        existingPaymentNotice.setPaymentStatus(paymentNotice.getPaymentStatus());
                    }

                    return existingPaymentNotice;
                }
            )
            .map(paymentNoticeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentNotice.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-notices} : get all the paymentNotices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentNotices in body.
     */
    @GetMapping("/payment-notices")
    public List<PaymentNotice> getAllPaymentNotices() {
        log.debug("REST request to get all PaymentNotices");
        return paymentNoticeRepository.findAll();
    }

    /**
     * {@code GET  /payment-notices/:id} : get the "id" paymentNotice.
     *
     * @param id the id of the paymentNotice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentNotice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-notices/{id}")
    public ResponseEntity<PaymentNotice> getPaymentNotice(@PathVariable Long id) {
        log.debug("REST request to get PaymentNotice : {}", id);
        Optional<PaymentNotice> paymentNotice = paymentNoticeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentNotice);
    }

    /**
     * {@code DELETE  /payment-notices/:id} : delete the "id" paymentNotice.
     *
     * @param id the id of the paymentNotice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-notices/{id}")
    public ResponseEntity<Void> deletePaymentNotice(@PathVariable Long id) {
        log.debug("REST request to delete PaymentNotice : {}", id);
        paymentNoticeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
