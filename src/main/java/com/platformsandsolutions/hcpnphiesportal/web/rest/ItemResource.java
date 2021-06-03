package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Item;
import com.platformsandsolutions.hcpnphiesportal.repository.ItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Item}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    private static final String ENTITY_NAME = "item";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemRepository itemRepository;

    public ItemResource(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * {@code POST  /items} : Create a new item.
     *
     * @param item the item to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new item, or with status {@code 400 (Bad Request)} if the item has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        if (item.getId() != null) {
            throw new BadRequestAlertException("A new item cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Item result = itemRepository.save(item);
        return ResponseEntity
            .created(new URI("/api/items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /items/:id} : Updates an existing item.
     *
     * @param id the id of the item to save.
     * @param item the item to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated item,
     * or with status {@code 400 (Bad Request)} if the item is not valid,
     * or with status {@code 500 (Internal Server Error)} if the item couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Item item)
        throws URISyntaxException {
        log.debug("REST request to update Item : {}, {}", id, item);
        if (item.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, item.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Item result = itemRepository.save(item);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, item.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /items/:id} : Partial updates given fields of an existing item, field will ignore if it is null
     *
     * @param id the id of the item to save.
     * @param item the item to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated item,
     * or with status {@code 400 (Bad Request)} if the item is not valid,
     * or with status {@code 404 (Not Found)} if the item is not found,
     * or with status {@code 500 (Internal Server Error)} if the item couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Item> partialUpdateItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Item item
    ) throws URISyntaxException {
        log.debug("REST request to partial update Item partially : {}, {}", id, item);
        if (item.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, item.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Item> result = itemRepository
            .findById(item.getId())
            .map(
                existingItem -> {
                    if (item.getSequence() != null) {
                        existingItem.setSequence(item.getSequence());
                    }
                    if (item.getIsPackage() != null) {
                        existingItem.setIsPackage(item.getIsPackage());
                    }
                    if (item.getTax() != null) {
                        existingItem.setTax(item.getTax());
                    }
                    if (item.getPayerShare() != null) {
                        existingItem.setPayerShare(item.getPayerShare());
                    }
                    if (item.getPatientShare() != null) {
                        existingItem.setPatientShare(item.getPatientShare());
                    }
                    if (item.getCareTeamSequence() != null) {
                        existingItem.setCareTeamSequence(item.getCareTeamSequence());
                    }
                    if (item.getTransportationSRCA() != null) {
                        existingItem.setTransportationSRCA(item.getTransportationSRCA());
                    }
                    if (item.getImaging() != null) {
                        existingItem.setImaging(item.getImaging());
                    }
                    if (item.getLaboratory() != null) {
                        existingItem.setLaboratory(item.getLaboratory());
                    }
                    if (item.getMedicalDevice() != null) {
                        existingItem.setMedicalDevice(item.getMedicalDevice());
                    }
                    if (item.getOralHealthIP() != null) {
                        existingItem.setOralHealthIP(item.getOralHealthIP());
                    }
                    if (item.getOralHealthOP() != null) {
                        existingItem.setOralHealthOP(item.getOralHealthOP());
                    }
                    if (item.getProcedure() != null) {
                        existingItem.setProcedure(item.getProcedure());
                    }
                    if (item.getServices() != null) {
                        existingItem.setServices(item.getServices());
                    }
                    if (item.getMedicationCode() != null) {
                        existingItem.setMedicationCode(item.getMedicationCode());
                    }
                    if (item.getServicedDate() != null) {
                        existingItem.setServicedDate(item.getServicedDate());
                    }
                    if (item.getServicedDateStart() != null) {
                        existingItem.setServicedDateStart(item.getServicedDateStart());
                    }
                    if (item.getServicedDateEnd() != null) {
                        existingItem.setServicedDateEnd(item.getServicedDateEnd());
                    }
                    if (item.getQuantity() != null) {
                        existingItem.setQuantity(item.getQuantity());
                    }
                    if (item.getUnitPrice() != null) {
                        existingItem.setUnitPrice(item.getUnitPrice());
                    }
                    if (item.getFactor() != null) {
                        existingItem.setFactor(item.getFactor());
                    }
                    if (item.getBodySite() != null) {
                        existingItem.setBodySite(item.getBodySite());
                    }
                    if (item.getSubSite() != null) {
                        existingItem.setSubSite(item.getSubSite());
                    }

                    return existingItem;
                }
            )
            .map(itemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, item.getId().toString())
        );
    }

    /**
     * {@code GET  /items} : get all the items.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of items in body.
     */
    @GetMapping("/items")
    public List<Item> getAllItems() {
        log.debug("REST request to get all Items");
        return itemRepository.findAll();
    }

    /**
     * {@code GET  /items/:id} : get the "id" item.
     *
     * @param id the id of the item to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the item, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        log.debug("REST request to get Item : {}", id);
        Optional<Item> item = itemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(item);
    }

    /**
     * {@code DELETE  /items/:id} : delete the "id" item.
     *
     * @param id the id of the item to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
