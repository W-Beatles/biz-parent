package cn.waynechu.order.api.runner;

import lombok.Builder;
import lombok.Data;

/**
 * @author Z-Beatles
 * @since 2021-05-24 22:56
 */
@Data
@Builder
public class TaskCreateBO {

    private String processor;

    private Integer shopId;

    private Integer role;
}
