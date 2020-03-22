package cn.waynechu.springcloud.common.model;

import cn.waynechu.springcloud.common.enums.ExportStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2020-03-22 17:58
 */
@Data
public class ExportResultModel implements Serializable {

    /**
     * 导出唯一标识
     */
    private String sid;

    /**
     * 导出状态
     */
    private ExportStatusEnum status;

    /**
     * 文件下载地址
     */
    private String url;
}
