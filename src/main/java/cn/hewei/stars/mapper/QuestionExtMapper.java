package cn.hewei.stars.mapper;

import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    Integer incView(Question record);

    int incCommentCount(Question question);

    List<Question> selectRelated(Question question);
}