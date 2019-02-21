package cn.waynechu.webcommon.web.form;

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
public class RadioInputControl extends BaseFormControl {

    private String value;

    private boolean checked;
}
