package cn.hewei.stars.dto;

import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-12 12:32
 * @Description
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
