package com.exc.domain.transaction;

import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CryptoCurrencyTransactionType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CryptoCurrencyTransaction.
 */
@MappedSuperclass
public abstract class CryptoCurrencyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

  /*  @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;*/

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "jhi_tx")
    private String tx;

    @Column(name = "message")
    private String message;

    @Column(name = "jhi_value")
    private BigInteger value;

    @Column(name = "external_id", nullable = false)
    private Long externalId;
    /**
     * if type is ORDER, then this field used in order to back track main order id from order service.
     * Used in monitor service, in order to callback order microservice in batch(1 shoot).
     */
    @Column(name = "order_pair_id")
    private Long orderPairId;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private CryptoCurrencyTransactionType type;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CryptoCurrencyTransactionStatus status;

/*    @ManyToOne
    private OrderPair orderPair;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public abstract Long getId();

    public abstract void setId(Long id);

    public Long getOrderPairId() {
        return orderPairId;
    }

    public void setOrderPairId(Long orderPairId) {
        this.orderPairId = orderPairId;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public CryptoCurrencyTransaction createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getTx() {
        return tx;
    }

    public CryptoCurrencyTransaction tx(String tx) {
        this.tx = tx;
        return this;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getMessage() {
        return message;
    }

    public CryptoCurrencyTransaction message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigInteger getValue() {
        return value;
    }

    public CryptoCurrencyTransaction value(BigInteger value) {
        this.value = value;
        return this;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public CryptoCurrencyTransactionType getType() {
        return type;
    }

    public CryptoCurrencyTransaction type(CryptoCurrencyTransactionType type) {
        this.type = type;
        return this;
    }

    public void setType(CryptoCurrencyTransactionType type) {
        this.type = type;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public CryptoCurrencyTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(CryptoCurrencyTransactionStatus status) {
        this.status = status;
    }


/*   public OrderPair getOrderPair() {
        return orderPair;
    }

    public CryptoCurrencyTransaction orderPair(OrderPair orderPair) {
        this.orderPair = orderPair;
        return this;
    }

    public void setOrderPair(OrderPair orderPair) {
        this.orderPair = orderPair;
    }*/
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        CryptoCurrencyTransaction cryptoCurrencyTransaction = (CryptoCurrencyTransaction) o;
        if (cryptoCurrencyTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cryptoCurrencyTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CryptoCurrencyTransaction{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", tx='" + getTx() + "'" +
            ", message='" + getMessage() + "'" +
            ", value=" + getValue() +
            ", type='" + getType() + "'" +
            "}";
    }
}
