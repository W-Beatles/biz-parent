package cn.waynechu.app.facade.model.common;

import cn.waynechu.common.page.Pagination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/15 13:42
 */
@Data
@ApiModel(description = "分页返回对象")
public abstract class AbstractPageModel<T extends AbstractModel> extends AbstractModel implements Serializable {

    @ApiModelProperty(value = "分页信息")
    private Pagination page;

    @ApiModelProperty(value = "分页数据")
    private List<T> rows = new ArrayList<>();

}
