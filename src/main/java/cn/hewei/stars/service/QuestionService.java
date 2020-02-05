package cn.hewei.stars.service;

import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.mapper.QuestionMapper;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-04 23:56
 * @Description
 */

public interface QuestionService {

    List<QuestionDTO> queryQuestions();
}
