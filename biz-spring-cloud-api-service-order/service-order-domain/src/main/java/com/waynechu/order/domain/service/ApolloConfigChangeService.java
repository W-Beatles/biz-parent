package com.waynechu.order.domain.service;

import com.waynechu.order.domain.configuration.ApolloRefreshProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @date 2020/5/15 15:19
 */
@Service
public class ApolloConfigChangeService {

    @Value("${apollo.refresh-value:-1}")
    private String refreshValue;

    @Autowired
    private ApolloRefreshProperty apolloRefreshProperty;

    public String refreshValue() {
        return refreshValue;
    }

    public String refreshConfigurationProperties() {
        return apolloRefreshProperty.getRefreshProperty();
    }

}
