package com.exc.domain.transaction;


import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@ApiModel(description = "btc transactions")
@Entity
@Table(name = "cctx_btc_open")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CryptoCurrencyTxBtcOpen extends CryptoCurrencyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cctx_btc_seq")
    @SequenceGenerator(name = "cctx_btc_seq", sequenceName = "cctx_btc_seq", allocationSize = 1)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
