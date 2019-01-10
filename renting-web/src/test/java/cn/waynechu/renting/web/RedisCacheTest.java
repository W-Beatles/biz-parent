package cn.waynechu.renting.web;

import cn.waynechu.boot.starter.common.util.RedisCache;
import cn.waynechu.renting.facade.dto.HouseDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/10 15:15
 */
public class RedisCacheTest extends RentingWebApplicationTests {

    @Autowired
    private RedisCache redisCache;

    @Test
    public void get() {
        List<HouseDTO> list = redisCache.getFromList("houses:1", HouseDTO.class);
        System.out.println(list);
    }

    @Test
    public void set() {
        ArrayList<HouseDTO> houseDTOS = new ArrayList<>();
        HouseDTO houseDTO;
        for (int i = 0; i < 9; i++) {
            houseDTO = new HouseDTO();
            houseDTO.setAdminId((long) i);
            houseDTO.setId((long) i);
            houseDTO.setBuildYear(i);
            houseDTOS.add(houseDTO);
        }
        redisCache.set("houses:1", houseDTOS);
    }
}
