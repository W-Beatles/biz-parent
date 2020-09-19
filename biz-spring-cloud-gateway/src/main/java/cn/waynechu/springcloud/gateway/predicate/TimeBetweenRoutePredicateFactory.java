package cn.waynechu.springcloud.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义基于开始时间和结束时间的谓词工厂
 * <p>
 * 只有当时间在配置的时间范围内网关才会转发
 *
 * @author zhuwei
 * @since 2020-02-23 22:56
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return serverWebExchange -> {
            LocalTime now = LocalTime.now();
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start", "end");
    }

    public static void main(String[] args) {
        /*
         * property的谓词配置示例:
         *
         * routes:
         *   - id: service-order
         *     uri: lb://service-order
         *     predicates:
         *       - TimeBetween=下午9:00,下午6:00
         */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(dateTimeFormatter.format(LocalTime.now()));
    }
}
