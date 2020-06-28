package cn.waynechu.utility.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.springcloud.common.util.LocalDateUtil;
import cn.waynechu.springcloud.common.util.LunarCalendar;
import cn.waynechu.utility.facade.response.CandlerItemResponse;
import cn.waynechu.utility.facade.response.CandlerResponse;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2020-04-18 18:39
 */
@Service
public class CandlerService {

    /**
     * 获取指定年月的日历信息
     *
     * @param year  年份
     * @param month 月
     * @return 日历信息
     */
    public CandlerResponse getCandler(Integer year, Integer month) {
        // 校验时间有效性
        LocalDate firstDay;
        try {
            firstDay = LocalDate.of(year, month, 1);
        } catch (DateTimeException e) {
            throw new BizException(BizErrorCodeEnum.REQUEST_PARAM_INVALID);
        }

        CandlerResponse candlerResponse = new CandlerResponse();
        // 设置月初第一天是周几
        int dayOfWeek = firstDay.getDayOfWeek().getValue();
        candlerResponse.setFirstDayOfWeek(dayOfWeek);
        // 获取一个月的最后一天是几号
        int lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        // 构造日历
        List<CandlerItemResponse> candlerItems = new ArrayList<>();
        CandlerItemResponse candlerItem;
        LunarCalendar lunarCalendar;
        for (int i = 1; i <= lastDay; i++) {
            candlerItem = new CandlerItemResponse();
            candlerItem.setSolarCalendar(i);

            lunarCalendar = new LunarCalendar(year, month, i);
            candlerItem.setLunarCalendar(LunarCalendar.getDayName(lunarCalendar.getDayOfLunarMonth()));
            candlerItem.setIsCurDay(LocalDateUtil.isCurDay(year, month, i) ? 1 : 0);
            candlerItem.setIsWeekend(LocalDateUtil.isIsWeekend(year, month, i) ? 1 : 0);
            candlerItems.add(candlerItem);
        }
        candlerResponse.setDayDates(candlerItems);
        return candlerResponse;
    }
}
