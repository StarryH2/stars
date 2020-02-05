package cn.hewei.stars.dto;

import cn.hewei.stars.model.User;
import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-04 23:52
 * @Description
 */
@Data
public class QuestionDTO {

    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
