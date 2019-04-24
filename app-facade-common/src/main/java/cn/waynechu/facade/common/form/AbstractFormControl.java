package cn.waynechu.facade.common.form;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * 多态表单类型定义
 *
 * @author zhuwei
 * @date 2019/2/21 13:53
 */

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextInputControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = RadioInputControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class),
        @JsonSubTypes.Type(value = AbstractFormControl.class)
})
public abstract class AbstractFormControl {
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
