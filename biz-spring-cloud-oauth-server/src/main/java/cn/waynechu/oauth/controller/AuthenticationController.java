package cn.waynechu.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhuwei
 * @since 2020/9/19 15:35
 */
@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public ModelAndView require() {
        return new ModelAndView("login");
    }
}
