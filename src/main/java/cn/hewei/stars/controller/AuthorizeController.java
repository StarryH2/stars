package cn.hewei.stars.controller;

import cn.hewei.stars.dto.AccessTokenDTD;
import cn.hewei.stars.dto.GitHubUser;
import cn.hewei.stars.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 何为
 * @Daet 2020-02-02 12:39
 * @Description
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.client.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTD accessTokenDTD = new AccessTokenDTD();
        accessTokenDTD.setClient_id(clientId);
        accessTokenDTD.setClient_secret(secret);
        accessTokenDTD.setCode(code);
        accessTokenDTD.setRedirect_uri(uri);
        accessTokenDTD.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTD);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getName());

        return "index";
    }

}


