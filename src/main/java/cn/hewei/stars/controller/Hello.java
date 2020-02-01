package cn.hewei.stars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 何为
 * @Daet 2020-01-31 22:04
 * @Description
 */
@Controller
public class Hello {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name")String name, Model model){
        model.addAttribute("name","何为");
        return "hello";
    }

}
