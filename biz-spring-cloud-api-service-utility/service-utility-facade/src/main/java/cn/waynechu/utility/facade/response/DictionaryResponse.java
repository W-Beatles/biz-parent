package cn.waynechu.utility.facade.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2020/7/3 18:13
 */
@Data
@ApiModel
public class DictionaryResponse {

    @ApiModelProperty("字典id")
    private Long id;

    @ApiModelProperty("类型编码")
    private String dicTypeCode;

    @ApiModelProperty("父节点id")
    private Long pid;

    @ApiModelProperty("字典编码")
    private String dicCode;

    @ApiModelProperty("字典值")
    private String dicValue;

    @ApiModelProperty("字典描述")
    private String dicDesc;

    @ApiModelProperty("是否固定: 0否 1是")
    private Integer fixedStatus;

    @ApiModelProperty("创建人")
    private String createdUser;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新人")
    private String updatedUser;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime updatedTime;
}
