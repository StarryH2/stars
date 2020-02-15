package cn.hewei.stars.mapper;

import cn.hewei.stars.model.Comment;
import cn.hewei.stars.model.CommentExample;
import cn.hewei.stars.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {

    int incCommentCount(Comment comment);

}