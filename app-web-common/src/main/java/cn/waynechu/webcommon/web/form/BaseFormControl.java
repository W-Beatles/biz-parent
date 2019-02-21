package cn.waynechu.webcommon.web.form;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/2/21 13:53
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextInputControl.class, name = BaseFormControl.INPUT_TYPE_TEXT),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.INPUT_TYPE_PASSWORD),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.INPUT_TYPE_EMAIL),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.INPUT_TYPE_URL),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.INPUT_TYPE_NUMBER),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.INPUT_TYPE_RANGE),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.INPUT_TYPE_DATE),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.FORM_TYPE_DATALIST),
        @JsonSubTypes.Type(value = RadioInputControl.class, name = BaseFormControl.INPUT_TYPE_RADIO),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.FORM_TYPE_SELECT),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.FORM_TYPE_TEXTAREA),
        @JsonSubTypes.Type(value = BaseFormControl.class, name = BaseFormControl.FORM_TYPE_FILE)
})
@Data
public abstract class BaseFormControl {
    // TODO: 2019/2/21 多态类型表单控件Model定义
    public static final String INPUT_TYPE_TEXT = "text";
    public static final String INPUT_TYPE_PASSWORD = "password";
    public static final String INPUT_TYPE_EMAIL = "email";
    public static final String INPUT_TYPE_URL = "url";
    public static final String INPUT_TYPE_NUMBER = "number";
    public static final String INPUT_TYPE_RANGE = "range";
    public static final String INPUT_TYPE_DATE = "date";
    public static final String INPUT_TYPE_RADIO = "radio";
    public static final String INPUT_TYPE_CHECKBOX = "checkbox";

    public static final String FORM_TYPE_DATALIST = "datalist";
    public static final String FORM_TYPE_SELECT = "select";
    public static final String FORM_TYPE_TEXTAREA = "textarea";
    public static final String FORM_TYPE_FILE = "file";

    private String type;

    private String name;
}
