package com.exc.service.node;

import com.exc.config.ApplicationProperties;
import com.exc.service.EncryptDecrypt;
import io.github.jhipster.config.JHipsterConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Component
@Profile("!" + JHipsterConstants.SPRING_PROFILE_TEST)
public class NodeETC extends NodeETH {

    public NodeETC(EncryptDecrypt encryptDecrypt, ApplicationProperties applicationProperties) {
        super(encryptDecrypt, applicationProperties);
    }

    @Override
    public void init() {
        web3 = Web3j.build(new HttpService(applicationProperties.getUrl("etc")));
    }

    @Override
    public Long getTxDelay() {
        return 9l;
    }
}
