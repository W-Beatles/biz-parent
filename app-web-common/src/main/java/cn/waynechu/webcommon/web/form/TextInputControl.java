package cn.waynechu.webcommon.web.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuwei
 * @date 2019/2/21 14:31
 */
@Getter
@Setter
public class TextInputControl extends AbstarctFormControl {

    private String value;

    private String placeholder;
}
