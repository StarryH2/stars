package cn.hewei.stars.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-05 15:09
 * @Description
 */
@Data
public class PaginationDTO<T> {

    private List<T> data;
    private Boolean showPrevious; //上一页按钮
    private Boolean showFirstPage;//首页按钮
    private Boolean showNext;//下一页按钮
    private Boolean showEndPage;//尾页按钮

    private Integer page;//当前页
    private List<Integer> pages = new ArrayList<>();

    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        //计算多少页
        totalPage = totalCount%size == 0 ? totalCount / size :totalCount / size + 1;
        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }


        //分页高亮显示赋值
        this.page = page;


        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //如果当前页为 1 不显示上一页图标
        showPrevious = page==1 ? false:true;

        //如果当前页为 最大页 不显示下一页图标
        showNext = page==totalPage ? false:true;

        //如果当页 含有 1 则不显示首页 图标
        showFirstPage = pages.contains(1) ? false:true;

        //如果当页 含有 最大页 则不显示尾页 图标
        showEndPage = pages.contains(totalCount) ? false:true;
    }
}
