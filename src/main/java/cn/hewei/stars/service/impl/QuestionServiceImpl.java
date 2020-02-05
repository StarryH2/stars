package cn.hewei.stars.service.impl;

import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.mapper.QuestionMapper;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-05 0:05
 * @Description
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<QuestionDTO> queryQuestions() {
        //创建QuestionDTO集合
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        //查询社区用户的发现
        List<Question> questions = questionMapper.queryQuestions();
        //遍历 questions
        for (Question question : questions) {
            //通过 question.getId 查询 User
            User user = userMapper.findById(question.getCreator());
            //创建 QuestionDTO类 作为存储 Question和User
            QuestionDTO questionDTO = new QuestionDTO();
            //使用BeanUtils工具类的copyProperties方法
            // 把 question上所有的属性拷贝到questionDTO对象上
            //提示1：最好在 QuestionDTO类上属性写Question类和User类比较好
            // -----而不是将Question类上的属性复制到QuestionDTO类上
            //存储Question
            BeanUtils.copyProperties(question,questionDTO);
            //存储User
            questionDTO.setUser(user);

            //将 QuestionDTO类 存入 QuestionDTO集合 中
            questionDTOs.add(questionDTO);
        }
        //返回 QuestionDTO集合
        return questionDTOs;
    }
}
