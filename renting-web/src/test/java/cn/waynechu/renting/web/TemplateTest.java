package cn.waynechu.renting.web;

import cn.waynechu.renting.web.model.ModelHouse;
import cn.waynechu.renting.web.service.HouseWebService;
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
        ModelHouse modelHouse = houseWebService.getById(33L);

        Context context = new Context();
        context.setVariable("modelHouse", modelHouse);
        String houseMail = templateEngine.process("houseMessage", context);
        System.out.println(houseMail);
    }
}
