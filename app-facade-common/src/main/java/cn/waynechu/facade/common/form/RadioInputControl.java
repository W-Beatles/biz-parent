package cn.waynechu.facade.common.form;

import lombok.Getter;
import lombok.Setter;

/**
 * 当选按钮
 *
 * @author zhuwei
 * @date 2019/2/21 14:31
 */
@Getter
@Setter
public class RadioInputControl extends AbstractFormControl {

    private String value;

    private boolean checked;
}
