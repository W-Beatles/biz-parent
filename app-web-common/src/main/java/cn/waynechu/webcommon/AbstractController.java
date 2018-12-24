package cn.waynechu.webcommon;

import cn.waynechu.common.util.DateTimeUtil;

import java.util.Date;

/**
 * @author zhuwei
 * @date 2018/12/24 18:02
 */
public class AbstractController {
    protected Date parseDateStartTime(Date srcDate) {
        return DateTimeUtil.formatDateToDate(srcDate, DateTimeUtil.FMT_DATE_DEFAULT);
    }

    protected Date parseDateEndTime(Date srcDate) {
        return DateTimeUtil.getDateEndDate(srcDate);
    }
}
