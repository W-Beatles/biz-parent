package cn.waynechu.springcloud.gateway.predicate;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

/**
 * 基于开始时间和结束时间的谓词配置
 *
 * @author zhuwei
 * @since 2020-02-23 22:57
 */
@Data
public class TimeBetweenConfig {

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime start;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime end;
}
