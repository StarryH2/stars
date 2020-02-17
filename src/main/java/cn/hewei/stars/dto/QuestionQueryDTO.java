package cn.hewei.stars.dto;

import lombok.Data;

/**
 * @Author 何为
 * @Daet 2020-02-19 13:11
 * @Description
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
