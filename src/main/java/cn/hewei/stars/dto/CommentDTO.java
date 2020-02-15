package cn.hewei.stars.dto;

import cn.hewei.stars.model.User;
import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-12 12:32
 * @Description
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
