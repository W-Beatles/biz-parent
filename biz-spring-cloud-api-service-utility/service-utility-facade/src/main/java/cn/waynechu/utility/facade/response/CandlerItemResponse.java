package cn.waynechu.utility.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuwei
 * @since 2020/4/9 10:19
 */
@Data
@ApiModel
public class CandlerItemResponse {

    @ApiModelProperty("阳历")
    private Integer solarCalendar;

    @ApiModelProperty("农历")
    private String lunarCalendar;

    @ApiModelProperty("是否周末: 0否 1是")
    private Integer isWeekend;

    @ApiModelProperty("是否当天: 0否 1是")
    private Integer isCurDay;
}
