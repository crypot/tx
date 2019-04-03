package com.exc.domain.transaction;


import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@ApiModel(description = "etc transactions")
@Entity
@Table(name = "cctx_etc_open")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CryptoCurrencyTxEtcOpen extends CryptoCurrencyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cctx_etc_seq")
    @SequenceGenerator(name = "cctx_etc_seq", sequenceName = "cctx_etc_seq", allocationSize = 1)
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
