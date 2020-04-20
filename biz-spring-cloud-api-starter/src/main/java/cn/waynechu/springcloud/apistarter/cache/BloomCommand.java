package cn.waynechu.springcloud.apistarter.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuwei
 * @date 2020/4/20 17:12
 */
@Getter
@AllArgsConstructor
public enum BloomCommand {

    /**
     * 布隆过滤器原语
     */
    RESERVE("BF.RESERVE"),
    ADD("BF.ADD"),
    MADD("BF.MADD"),
    EXISTS("BF.EXISTS"),
    MEXISTS("BF.MEXISTS");

    private String command;

}
