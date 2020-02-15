package cn.hewei.stars.service;

import cn.hewei.stars.dto.PaginationDTO;
import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.model.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-04 23:56
 * @Description
 */

public interface QuestionService {


    PaginationDTO queryQuestions(Integer page, Integer size);

    PaginationDTO queryQuestionsByUserId(Long userId, Integer page, Integer size);

    QuestionDTO getById(Long id);

    void createOrUpdate(Question question);

    void incView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
