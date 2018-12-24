package cn.waynechu.common.proxy;

import cn.waynechu.common.web.Result;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuwei
 * @date 2018/11/7 11:00
 */
@Slf4j
public class SubImpl implements Sub {

    @Override
    public Result print(String str) {
        log.info("call print method, arg is {}", str);
        return Result.success();
    }

    public static void main(String[] args) {
        Sub sub = (Sub) MethodPrintAnnotationProxy.getInstance().bind(new SubImpl());
        sub.print("sub");
    }
}
