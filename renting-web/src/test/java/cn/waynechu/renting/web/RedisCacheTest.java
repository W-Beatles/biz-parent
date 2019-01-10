package cn.waynechu.renting.web;

import cn.waynechu.boot.starter.common.util.RedisCache;
import cn.waynechu.renting.dal.entity.House;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhuwei
 * @date 2019/1/10 15:15
 */
public class RedisCacheTest extends RentingWebApplicationTests {

    @Autowired
    private RedisCache redisCache;

    @Test
    public void get() {
        House house = redisCache.get("house:34", House.class);
        System.out.println(house.toString());
    }
}
