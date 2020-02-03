package cn.hewei.stars.controller;

import cn.hewei.stars.dto.AccessTokenDTD;
import cn.hewei.stars.dto.GitHubUser;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.User;
import cn.hewei.stars.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author 何为
 * @Daet 2020-02-02 12:39
 * @Description
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Resource
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.client.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){
        AccessTokenDTD accessTokenDTD = new AccessTokenDTD();
        accessTokenDTD.setClient_id(clientId);
        accessTokenDTD.setClient_secret(secret);
        accessTokenDTD.setCode(code);
        accessTokenDTD.setRedirect_uri(uri);
        accessTokenDTD.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTD);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if (gitHubUser!=null){
            //登陆成功 写 cookie 和 session
            request.getSession().setAttribute("user",gitHubUser);
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            userMapper.insert(user);
            return "redirect:/";
        }else {
            //登陆失败
            return "redirect:/";
        }

    }

}


