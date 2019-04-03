package com.exc.service;

import com.exc.domain.CurrencyName;
import com.exc.service.dto.GenWalletDTO;
import com.exc.service.node.INode;
import com.exc.service.node.NodeAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KeyService {
    private final EncryptDecrypt encryptDecrypt;
    private final Logger log = LoggerFactory.getLogger(KeyService.class);
    private final NodeAdapterFactory nodeAdapterFactory;

    public KeyService(NodeAdapterFactory nodeAdapterFactory, EncryptDecrypt encryptDecrypt) {
        this.nodeAdapterFactory = nodeAdapterFactory;
        this.encryptDecrypt = encryptDecrypt;
    }


    /**
     * generate wallets only, does not create records in DB.
     *
     * @param userId
     * @param currencies
     * @return
     */
    public List<GenWalletDTO> genWallets(Long userId, List<CurrencyName> currencies) {
        log.debug("Request to generate new wallets ");

        if (currencies == null || currencies.size() == 0) {
            currencies = Arrays.asList(CurrencyName.values());
        }

        List<GenWalletDTO> res = new ArrayList<>(currencies.size());

        for (CurrencyName cur : currencies) {
            GenWalletDTO rs = new GenWalletDTO();
            INode in = nodeAdapterFactory.getNode(cur);
            // rs.setCurName(nodes.get(cur));
            rs.setPrivateKey(in.genKey(String.valueOf(userId)));
            switch (cur) {
                case BTC:
                    rs.setPublicKey(in.toPublic(String.valueOf(userId)));
                    break;
                default:
                    rs.setPublicKey(in.toPublic(rs.getPrivateKey()));
                    break;
            }


            res.add(rs);
            rs.setPrivateKey(encryptDecrypt.encrypt(rs.getPrivateKey()));
            rs.setCurrencyName(cur);
        }

        log.debug("Generated {} new wallets ", res.size());

        return res;

    }
}
