package com.exc.service.remote;

import com.exc.client.AuthorizedFeignClient;
import com.exc.config.ServiceName;
import com.exc.service.dto.remote.OrderMigrateRequestDTO;

import java.util.Map;

@AuthorizedFeignClient(name = ServiceName.ORDER)
public interface OpService {
    Map<Long, Long> migrate(OrderMigrateRequestDTO orderMigrateRequestDTO);
}
