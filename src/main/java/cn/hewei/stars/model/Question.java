package cn.hewei.stars.model;


import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-04 13:29
 * @Description
 */
@Data
public class Question {

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
}
