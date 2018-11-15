package cn.waynechu.facade.request.common;

import cn.waynechu.api.common.page.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/11/15 11:56
 */
@Data
@ApiModel(description = "分页请求对象")
public abstract class AbstractPageRequest extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 2789603649153042060L;

    @ApiModelProperty(value = "当前页，默认第一页")
    private int curPage = 1;

    @ApiModelProperty(value = "每页条数，默认20条")
    private int pageSize = 20;

    @JsonIgnore
    public Pagination getPagination() {
        return new Pagination(this.pageSize, this.curPage, this.pageSize);
    }
}
