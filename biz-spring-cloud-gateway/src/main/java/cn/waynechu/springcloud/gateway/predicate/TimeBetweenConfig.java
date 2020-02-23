package cn.waynechu.springcloud.gateway.predicate;

import lombok.Data;

import java.time.LocalTime;

/**
 * 基于开始时间和结束时间的谓词配置
 *
 * @author zhuwei
 * @date 2020-02-23 22:57
 */
@Data
public class TimeBetweenConfig {

    private LocalTime start;

    private LocalTime end;
}
