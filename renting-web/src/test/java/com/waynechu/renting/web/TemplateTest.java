package com.waynechu.renting.web;

import com.waynechu.renting.web.response.HouseBizResponse;
import com.waynechu.renting.web.service.HouseWebService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author zhuwei
 * @date 2019/3/27 11:38
 */
public class TemplateTest extends RentingWebApplicationTests {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private HouseWebService houseWebService;

    @Test
    public void generateTemplateMessage() {
        HouseBizResponse houseResponse = houseWebService.getById(33L);

        Context context = new Context();
        context.setVariable("modelHouse", houseResponse);
        String houseMail = templateEngine.process("houseMessage", context);
        System.out.println(houseMail);
    }
}
