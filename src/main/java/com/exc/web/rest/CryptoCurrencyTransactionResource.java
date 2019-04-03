package com.exc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.exc.service.CryptoCurrencyTransactionService;
import com.exc.web.rest.errors.BadRequestAlertException;
import com.exc.web.rest.util.HeaderUtil;
import com.exc.web.rest.util.PaginationUtil;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CryptoCurrencyTransaction.
 */
@RestController
@RequestMapping("/api")
public class CryptoCurrencyTransactionResource {

  /*  private final Logger log = LoggerFactory.getLogger(CryptoCurrencyTransactionResource.class);

    private static final String ENTITY_NAME = "txCryptoCurrencyTransaction";

    private CryptoCurrencyTransactionService cryptoCurrencyTransactionService;

    public CryptoCurrencyTransactionResource(CryptoCurrencyTransactionService cryptoCurrencyTransactionService) {
        this.cryptoCurrencyTransactionService = cryptoCurrencyTransactionService;
    }

    *//**
     * POST  /crypto-currency-transactions : Create a new cryptoCurrencyTransaction.
     *
     * @param cryptoCurrencyTransactionDTO the cryptoCurrencyTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cryptoCurrencyTransactionDTO, or with status 400 (Bad Request) if the cryptoCurrencyTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*
    @PostMapping("/crypto-currency-transactions")
    @Timed
    public ResponseEntity<CryptoCurrencyTransactionDTO> createCryptoCurrencyTransaction(@RequestBody CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save CryptoCurrencyTransaction : {}", cryptoCurrencyTransactionDTO);
        if (cryptoCurrencyTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new cryptoCurrencyTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CryptoCurrencyTransactionDTO result = cryptoCurrencyTransactionService.save(cryptoCurrencyTransactionDTO);
        return ResponseEntity.created(new URI("/api/crypto-currency-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    *//**
     * PUT  /crypto-currency-transactions : Updates an existing cryptoCurrencyTransaction.
     *
     * @param cryptoCurrencyTransactionDTO the cryptoCurrencyTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cryptoCurrencyTransactionDTO,
     * or with status 400 (Bad Request) if the cryptoCurrencyTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the cryptoCurrencyTransactionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*
    @PutMapping("/crypto-currency-transactions")
    @Timed
    public ResponseEntity<CryptoCurrencyTransactionDTO> updateCryptoCurrencyTransaction(@RequestBody CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update CryptoCurrencyTransaction : {}", cryptoCurrencyTransactionDTO);
        if (cryptoCurrencyTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CryptoCurrencyTransactionDTO result = cryptoCurrencyTransactionService.save(cryptoCurrencyTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cryptoCurrencyTransactionDTO.getId().toString()))
            .body(result);
    }

    *//**
     * GET  /crypto-currency-transactions : get all the cryptoCurrencyTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cryptoCurrencyTransactions in body
     *//*
    @GetMapping("/crypto-currency-transactions")
    @Timed
    public ResponseEntity<List<CryptoCurrencyTransactionDTO>> getAllCryptoCurrencyTransactions(Pageable pageable) {
        log.debug("REST request to get a page of CryptoCurrencyTransactions");
        Page<CryptoCurrencyTransactionDTO> page = cryptoCurrencyTransactionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/crypto-currency-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    *//**
     * GET  /crypto-currency-transactions/:id : get the "id" cryptoCurrencyTransaction.
     *
     * @param id the id of the cryptoCurrencyTransactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cryptoCurrencyTransactionDTO, or with status 404 (Not Found)
     *//*
    @GetMapping("/crypto-currency-transactions/{id}")
    @Timed
    public ResponseEntity<CryptoCurrencyTransactionDTO> getCryptoCurrencyTransaction(@PathVariable Long id) {
        log.debug("REST request to get CryptoCurrencyTransaction : {}", id);
        Optional<CryptoCurrencyTransactionDTO> cryptoCurrencyTransactionDTO = cryptoCurrencyTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cryptoCurrencyTransactionDTO);
    }

    *//**
     * DELETE  /crypto-currency-transactions/:id : delete the "id" cryptoCurrencyTransaction.
     *
     * @param id the id of the cryptoCurrencyTransactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     *//*
    @DeleteMapping("/crypto-currency-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCryptoCurrencyTransaction(@PathVariable Long id) {
        log.debug("REST request to delete CryptoCurrencyTransaction : {}", id);
        cryptoCurrencyTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }*/
}
