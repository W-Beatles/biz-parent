package cn.waynechu.springcloud.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @date 2019/11/28 11:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalDateTimeRange {

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
