package cn.hewei.stars.dto;

import cn.hewei.stars.model.User;
import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-15 19:37
 * @Description
 */
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;

}
