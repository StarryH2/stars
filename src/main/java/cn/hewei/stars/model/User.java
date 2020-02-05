package cn.hewei.stars.model;

import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-02 19:52
 * @Description
 */

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;

}
