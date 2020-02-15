package cn.hewei.stars.controller;

import cn.hewei.stars.cache.TagCache;
import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.dto.TagDTO;
import cn.hewei.stars.mapper.QuestionMapper;
import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
                            Question question,
                            HttpServletRequest request,
                            Model model){
        //如果出现错误，依然停留在publish.html页面显示用户输入的数据
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("tags", TagCache.get());

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
        String invalid = TagCache.filterInvalid(question.getTag());
        if (StringUtils.isNoneBlank(invalid)){
            model.addAttribute("error","输入标签要规范 "+invalid);
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

        question.setId(question.getId());
        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}
