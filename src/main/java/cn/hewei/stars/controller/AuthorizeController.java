package cn.hewei.stars.controller;

import cn.hewei.stars.dto.AccessTokenDTO;
import cn.hewei.stars.dto.GitHubUser;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.User;
import cn.hewei.stars.provider.GitHubProvider;
import cn.hewei.stars.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author 何为
 * @Daet 2020-02-02 12:39
 * @Description
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.client.uri}")
    private String uri;

    //用户点击登陆 使用GitHub
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser!=null && gitHubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));

            user.setBio(gitHubUser.getBio());
            user.setAvatarUrl(gitHubUser.getAvatar_url());

            userService.createOrUpdate(user);
            //登陆成功 写 cookie 和 session
            //request.getSession().setAttribute("user",gitHubUser);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            log.error("callback get github error,{}",gitHubUser);
            //登陆失败
            return "redirect:/";
        }
    }

    /**
     * @param request
     * @return
     * 退出登陆
     * 移除session和cookie
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}


