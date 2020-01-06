package cn.waynechu.springcloud.common.listener;

import lombok.Data;

import java.util.Date;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 * @author zhuwei
 * @date 2020/1/6 19:02
 */
@Data
public class DemoData {

    private String string;

    private Date date;

    private Double doubleData;
}
