package cn.waynechu.facade.common.form;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单选按钮类型
 *
 * @author zhuwei
 * @date 2019/2/21 14:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("radio")
public class RadioInputControl extends AbstractFormControl {

    private String value;

    private boolean checked;
}
