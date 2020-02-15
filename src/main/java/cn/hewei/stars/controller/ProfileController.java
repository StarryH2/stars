package cn.hewei.stars.controller;

import cn.hewei.stars.dto.NotificationDTO;
import cn.hewei.stars.dto.PaginationDTO;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.Notification;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.NotificationService;
import cn.hewei.stars.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-07 15:24
 * @Description
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size){

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.queryQuestionsByUserId(user.getId(), page, size);
            System.out.println(action);
            System.out.println("测试-"+paginationDTO.getShowPrevious());
            model.addAttribute("paginationDTO",paginationDTO);
        }else if ("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("paginationDTO",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
