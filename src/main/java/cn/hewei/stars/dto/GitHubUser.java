package cn.hewei.stars.dto;

import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-02 13:48
 * @Description 用户登陆
 */
@Data
public class GitHubUser {
    private  String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
