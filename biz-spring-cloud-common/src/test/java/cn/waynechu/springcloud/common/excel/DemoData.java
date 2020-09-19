package cn.waynechu.springcloud.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 基础数据类
 * <pre>
 * 匹配方式:
 *   1.字段的排序和excel里面的排序一致，可以不用添加  @ExcelProperty 注解
 *   2.用名字去匹配。添加 @ExcelProperty("字符串标题") 并指定标题。
 *     这里需要注意，如果名字重复，会导致只有一个字段读取到数据
 *   3.指定下标去匹配。添加 @ExcelProperty(index = 2) 并指定下标，将强制读取第三个。
 *     这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
 * </pre>
 *
 * @author zhuwei
 * @since 2020/1/6 19:02
 */
@Data
public class DemoData {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;
}
