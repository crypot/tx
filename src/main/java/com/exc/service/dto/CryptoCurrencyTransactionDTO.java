package com.exc.service.dto;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CryptoCurrencyTransactionType;
import com.exc.domain.CurrencyName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the CryptoCurrencyTransaction entity.
 */
public class CryptoCurrencyTransactionDTO implements Serializable {

    private Long id;

    private CurrencyName currencyName;

    private ZonedDateTime createDate;

    private String tx;

    private String message;

    private BigDecimal value;

    private Long userInfoId;

    private String externalId;

    private CryptoCurrencyTransactionType type;

    private CryptoCurrencyTransactionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public CryptoCurrencyTransactionType getType() {
        return type;
    }

    public void setType(CryptoCurrencyTransactionType type) {
        this.type = type;
    }

    public CryptoCurrencyTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(CryptoCurrencyTransactionStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CryptoCurrencyTransactionDTO cryptoCurrencyTransactionDTO = (CryptoCurrencyTransactionDTO) o;
        if (cryptoCurrencyTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cryptoCurrencyTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CryptoCurrencyTransactionDTO{" +
            "id=" + getId() +
            ", currencyName='" + getCurrencyName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", tx='" + getTx() + "'" +
            ", message='" + getMessage() + "'" +
            ", value=" + getValue() +
            ", userInfoId=" + getUserInfoId() +
            ", externalId='" + getExternalId() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
