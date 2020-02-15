package cn.hewei.stars.controller;

import cn.hewei.stars.dto.PaginationDTO;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 何为
 * @Daet 2020-02-01 14:19
 * @Description
 */
@Controller
public class IndexController {
    @Resource
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){

        //用户登陆查询社区用户的发现
        PaginationDTO paginationDTO = questionService.queryQuestions(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
