package com.waynechu.renting.web;

import cn.waynechu.springcloud.apicommon.util.RedisCache;
import com.waynechu.renting.facade.dto.HouseDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhuwei
 * @date 2019/1/10 15:15
 */
public class RedisCacheTest extends RentingWebApplicationTests {

    @Autowired
    private RedisCache redisCache;

    @Test
    public void set() {
        for (int i = 1; i <= 8; i++) {
            HouseDTO houseDTO = new HouseDTO();
            houseDTO.setAdminId((long) i);
            redisCache.set("house:" + i, houseDTO);
        }
    }

    @Test
    public void get() {
        HouseDTO houseDTO = redisCache.get("house:1", HouseDTO.class);
        Assert.assertEquals(Long.valueOf(1), houseDTO.getAdminId());
    }

    @Test
    public void setList() {
        ArrayList<HouseDTO> houseDTOS = new ArrayList<>();
        HouseDTO houseDTO;
        for (int i = 0; i < 9; i++) {
            houseDTO = new HouseDTO();
            houseDTO.setAdminId((long) i);
            houseDTO.setId((long) i);
            houseDTO.setBuildYear(i);
            houseDTOS.add(houseDTO);
        }
        redisCache.set("houses", houseDTOS);
    }

    @Test
    public void getToList() {
        List<HouseDTO> houseDTOS = redisCache.getToList("houses", HouseDTO.class);
        Assert.assertEquals(9, houseDTOS.size());
    }

    @Test
    public void multiGet() {
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            keys.add("house:" + i);
        }
        List<HouseDTO> houseDTOS = redisCache.multiGet(keys, HouseDTO.class);
        System.out.println(houseDTOS);
    }

    @Test
    public void setWithTimeOut() {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setAdminId(1L);
        redisCache.set("house:2", houseDTO, 60);
    }

    @Test
    public void setIfAbsent() {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setAdminId(1L);
        Boolean flag = redisCache.setIfAbsent("house:1", houseDTO);
        Boolean flag2 = redisCache.setIfAbsent("house:2", houseDTO);
        Assert.assertEquals(false, flag);
        Assert.assertEquals(true, flag2);
    }

    @Test
    public void increment() {
        Long increment = redisCache.increment("index:count");
        Assert.assertEquals(Long.valueOf(2), increment);
    }

    @Test
    public void incrementLongDelta() {
        Long increment = redisCache.increment("index:count", 5L);
        Assert.assertEquals(Long.valueOf(7), increment);
    }

    @Test
    public void incrementDoubleDelta() {
        Double increment = redisCache.increment("index:count", 1.8);
        Assert.assertEquals(Double.valueOf(8.8), increment);
    }

    @Test
    public void getAndSet() {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setAdminId(3L);
        HouseDTO oldHouseDTO = redisCache.getAndSet("house:4", houseDTO, HouseDTO.class);
        Assert.assertNull(oldHouseDTO);
    }

    @Test
    public void delete() {
        boolean delete3 = redisCache.delete("house:1");
        boolean delete2 = redisCache.delete("houses");
        Assert.assertTrue(delete3);
        Assert.assertTrue(delete2);
    }

    @Transactional
    @Test
    public void deleteTransaction() {
        boolean delete3 = redisCache.delete("house:1");
        boolean delete2 = redisCache.delete("houses");
        Assert.assertTrue(delete3);
        Assert.assertTrue(delete2);
    }

    @Test
    public void deleteBatch() {
        Long count = redisCache.delete(new ArrayList<String>() {{
            add("house:1");
            add("house:2");
            add("house:3");
            add("house:4");
        }});
        Assert.assertEquals(Long.valueOf(2), count);
    }

    @Test
    public void testGetLock() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        int threadNum = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.submit(() -> {
                String requestId = Thread.currentThread().getName();
                boolean getLock = redisCache.getLock("test:lock", requestId, 1000L);
                if (getLock) {
                    System.out.println(requestId + ": get lock");
                    boolean delSuccess = redisCache.delLock("test:lock", requestId);
                    System.out.println(requestId + ": del lock - " + delSuccess);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    @Test
    public void isExist() {
        boolean exist = redisCache.isExist("house:1");
        Assert.assertFalse(exist);
    }

    @Test
    public void delLock() {
        boolean exist = redisCache.delLock("test:lock", "pool-5-thread-283");
        System.out.println(exist);
    }
}
