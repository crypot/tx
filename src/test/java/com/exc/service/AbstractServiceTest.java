package com.exc.service;

import com.exc.TxApp;
import com.exc.config.LocalNode;
import com.exc.domain.CryptoCurrencyTransactionStatus;
import com.exc.domain.CryptoCurrencyTransactionType;
import com.exc.domain.CurrencyName;
import com.exc.service.dto.CryptoCurrencyTransactionDTO;
import com.exc.service.node.NodeAdapterFactory;
import com.exc.service.remote.OpService;
import io.github.jhipster.config.JHipsterConstants;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TxApp.class)
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY)
@ActiveProfiles({JHipsterConstants.SPRING_PROFILE_TEST})
public abstract class AbstractServiceTest {

    @Autowired
    protected LocalNode localNode;

    @MockBean
    protected OpService userService;

    @MockBean
    protected NodeAdapterFactory nodeAdapterFactory;


    protected CryptoCurrencyTransactionDTO tx = new CryptoCurrencyTransactionDTO();
    protected CurrencyName buy = CurrencyName.ETH, sell = CurrencyName.BTC;
    protected Long txId = 1l;

    @Before
    public void setup() {
        tx.setCurrencyName(buy);
        tx.setUserInfoId(1l);
        tx.setCreateDate(ZonedDateTime.now());
        tx.setExternalId("1");
        tx.setType(CryptoCurrencyTransactionType.ORDER);
        tx.setStatus(CryptoCurrencyTransactionStatus.IN_PROCESS);

        when(nodeAdapterFactory.getNode(any())).thenReturn(localNode);

    }

}
