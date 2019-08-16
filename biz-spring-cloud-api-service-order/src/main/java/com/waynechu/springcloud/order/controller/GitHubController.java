package com.waynechu.springcloud.order.controller;

import com.waynechu.springcloud.order.remote.GitHubRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuwei
 * @date 2019/4/25 15:43
 */
@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    private GitHubRemote gitHubRemote;

    @GetMapping("/search")
    public String search(@RequestParam("name") String name) {
        return gitHubRemote.searchRepo(name);
    }
}
