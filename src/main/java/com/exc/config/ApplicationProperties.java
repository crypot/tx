package com.exc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Properties specific to Tx.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private Map<String, String> currencies = new HashMap<>();
    private Map<String, String> adminkeys = new HashMap<>();

    public Map<String, String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies;
    }

    public String getUrl(String currency) {
        return currencies.get(currency);
    }

    public String getAdminPubKey(String currency) {
        return adminkeys.get(currency);
    }

    public Map<String, String> getAdminkeys() {
        return adminkeys;
    }

    public void setAdminkeys(Map<String, String> adminkeys) {
        this.adminkeys = adminkeys;
    }


}
