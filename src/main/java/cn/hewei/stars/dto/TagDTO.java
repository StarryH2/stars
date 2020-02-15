package cn.hewei.stars.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-14 20:53
 * @Description
 */
@Data
public class TagDTO {

    private  String categoryName;
    private List<String> tags;

}
