package com.exc.web.rest;

import com.exc.domain.CurrencyName;
import com.exc.service.KeyService;
import com.exc.service.dto.GenWalletDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * uses to manage keys as microservice
 */
@RestController
@RequestMapping("/api")
public class KeyResource {
    private final Logger log = LoggerFactory.getLogger(KeyResource.class);
    private final KeyService keyService;

    public KeyResource(KeyService keyService) {
        this.keyService = keyService;
    }

    @PostMapping("/api/internal/gen-wallets/{id}")
    public ResponseEntity<List<GenWalletDTO>> getAllCryptoCurrencyTransactions(@PathVariable("id") Long userId, @RequestBody List<CurrencyName> currencies) {
        log.debug("REST request to generate set of currencies");
        return ResponseEntity.ok(keyService.genWallets(userId, currencies));
    }
}

