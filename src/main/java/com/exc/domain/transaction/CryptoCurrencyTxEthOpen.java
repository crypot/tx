package com.exc.domain.transaction;


import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@ApiModel(description = "eth transactions")
@Entity
@Table(name = "cctx_eth_open")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CryptoCurrencyTxEthOpen extends CryptoCurrencyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cctx_eth_seq")
    @SequenceGenerator(name = "cctx_eth_seq", sequenceName = "cctx_eth_seq", allocationSize = 1)
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