package com.exc.service.node;

import com.exc.domain.CurrencyName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NodeAdapterFactory {
    private final Logger log = LoggerFactory.getLogger(NodeAdapterFactory.class);
    private final NodeBTC nodeBTC;
    private final NodeETH nodeETH;
    private final NodeETC nodeETC;

    public NodeAdapterFactory(NodeBTC nodeBTC, NodeETH nodeETH, NodeETC nodeETC) {
        this.nodeBTC = nodeBTC;
        this.nodeETH = nodeETH;
        this.nodeETC = nodeETC;
    }

    public INode getNode(CurrencyName currencyName) {
        log.debug("request to get node for currency {}", currencyName);
        INode res = null;
        switch (currencyName) {
            case BTC:
                res = nodeBTC;
                break;

            case ETC:
                res = nodeETC;
                break;

            case ETH:
                res = nodeETH;
                break;
        }
        return res;
    }
}
