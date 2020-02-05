package cn.hewei.stars.controller;

import cn.hewei.stars.mapper.QuestionMapper;
import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author 何为
 * @Daet 2020-02-03 23:56
 * @Description
 */
@Controller
public class PublishController {

    @Resource
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, HttpServletRequest request, Model model){
        //如果出现错误，依然停留在publish.html页面显示用户输入的数据
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());

        //后端判断用户传的值是否为null
        if (question.getTitle()=="" || question.getTitle()==null){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (question.getDescription()=="" || question.getDescription()==null){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (question.getTag()=="" || question.getTag()==null){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        //questionMapper.create(question);
        //验证用户算是否登陆，如果未登陆，提示用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录...");
            return "publish";
        }
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
