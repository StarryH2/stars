package cn.hewei.stars.mapper;

import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-04 13:23
 * @Description
 */
@Mapper
public interface QuestionMapper {

    //添加用户发现
    @Insert("insert into question values(DEFAULT,#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create (Question question);

    //查询社区用户的发现
    @Select("select * from question")
    List<Question> queryQuestions();
}
