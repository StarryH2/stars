package cn.hewei.stars.controller;

import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.mapper.QuestionMapper;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-01 14:19
 * @Description
 */
@Controller
public class IndexController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        //获取浏览器所有cookie
        Cookie[] cookies = request.getCookies();
        //预防浏览器没有cookie
        if (cookies!=null && cookies.length!=0){
            //遍历所有cookie
            for (Cookie cookie : cookies) {
                //如果浏览器中 cookie名 是 token
                if ("token".equals(cookie.getName())){
                    //获取value
                    String token = cookie.getValue();
                    //用token的value查询数据库中是否存在
                    User user = userMapper.findByToken(token);
                    //如果存在
                    if (user!=null){
                        //在导航栏右上角显示用户信息
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        //用户登陆查询社区用户的发现
        List<QuestionDTO> questions = questionService.queryQuestions();
        model.addAttribute("questions",questions);
        return "index";
    }
}
