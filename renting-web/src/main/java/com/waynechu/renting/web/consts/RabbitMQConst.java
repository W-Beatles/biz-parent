package com.waynechu.renting.web.consts;

/**
 * @author waynechu
 * @date 2019-01-12 15:41
 */
public class RabbitMQConst {

    public interface Exchange {
        String DIRECT_RENTING = "test.direct.renting";
    }

    public interface Queue {
        String DIRECT_RENTING = "test.direct.renting";
    }

    public interface RoutingKey {
        String DIRECT_RENTING = "test.direct.renting";
    }
}
