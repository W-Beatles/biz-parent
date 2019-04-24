package cn.waynechu.facade.common.form;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文本输入框类型
 *
 * @author zhuwei
 * @date 2019/2/21 14:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("text")
public class TextInputControl extends AbstractFormControl {

    private String value;

    private String placeholder;
}
