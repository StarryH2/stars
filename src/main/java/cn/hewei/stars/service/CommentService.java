package cn.hewei.stars.service;

import cn.hewei.stars.dto.CommentDTO;
import cn.hewei.stars.enums.CommentTypeEnum;
import cn.hewei.stars.model.Comment;
import cn.hewei.stars.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-12 13:40
 * @Description
 */
public interface CommentService {

    @Transactional
    void insert(Comment commnet, User user);

    List<CommentDTO> findByTargetId(Long id, CommentTypeEnum type);
}
