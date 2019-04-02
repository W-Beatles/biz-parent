package com.waynechu.renting.web.controller;

import com.waynechu.renting.web.response.HouseResponse;
import com.waynechu.renting.web.service.HouseWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhuwei
 * @date 2019/3/27 12:47
 */
@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private HouseWebService houseWebService;

    @GetMapping("/houses/{id}")
    public String houseMessage(@PathVariable Long id, Model model) {
        HouseResponse houseResponse = houseWebService.getById(id);
        model.addAttribute("houseResponse", houseResponse);
        return "houseMessage";
    }
}
