package cn.hewei.stars.provider;

import cn.hewei.stars.dto.AccessTokenDTD;
import cn.hewei.stars.dto.GitHubUser;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @Author 何为
 * @Daet 2020-02-02 12:49
 * @Description
 */
@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTD accessTokenDTD){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTD));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            return null;
        }


    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            return null;
        }

    }
}
