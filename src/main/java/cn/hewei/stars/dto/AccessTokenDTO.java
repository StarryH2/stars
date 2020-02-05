package cn.hewei.stars.dto;

import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-02 12:57
 * @Description github访问令牌
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
