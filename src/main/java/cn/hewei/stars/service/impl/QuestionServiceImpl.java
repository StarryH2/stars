package cn.hewei.stars.service.impl;

import cn.hewei.stars.dto.NotificationDTO;
import cn.hewei.stars.dto.PaginationDTO;
import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.dto.QuestionQueryDTO;
import cn.hewei.stars.exception.CustomizeErrorCode;
import cn.hewei.stars.exception.CustomizeException;
import cn.hewei.stars.mapper.QuestionExtMapper;
import cn.hewei.stars.mapper.QuestionMapper;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.Question;
import cn.hewei.stars.model.QuestionExample;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Override
    public PaginationDTO queryQuestions(String search,Integer page, Integer size) {
        //如歌tag为空 就返回空
        if (StringUtils.isNoneBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        //分页数据
        PaginationDTO paginationDTO = new PaginationDTO();
        //所有的数量
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        paginationDTO.setPagination(totalCount,page,size);
        //预防用户在网页输入页数出现问题进行判断
        if (page<1){
            page = 1;
        }
        if (page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        //分页
        Integer offset = size * (page-1);

        //创建QuestionDTO集合
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        //查询社区用户的发现-分页
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("GMT_CREATE DESC");
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        //遍历 questions
        for (Question question : questions) {
            //通过 question.getId 查询 User
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

        //返回 PaginationDTO 分页
        paginationDTO.setData(questionDTOs);

        return paginationDTO;
    }

    @Override
    public PaginationDTO queryQuestionsByUserId(Long userId, Integer page, Integer size) {
        //分页数据
        PaginationDTO paginationDTO = new PaginationDTO();
        //所有的数量
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount =(int) questionMapper.countByExample(questionExample);
        paginationDTO.setPagination(totalCount,page,size);
        //预防用户在网页输入页数出现问题进行判断
        if (page<1){
            page = 1;
        }
        if (page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //分页
        Integer offset = size * (page-1);

        //创建QuestionDTO集合
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        //查询社区用户的发现+分页
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(
                example, new RowBounds(offset, size));

        //遍历 questions
        for (Question question : questions) {
            //通过 question.getId 查询 User
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

        //返回 PaginationDTO 分页
        paginationDTO.setData(questionDTOs);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        //根据id查询 疑问表
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question,questionDTO);
        //通过疑问表的creator查询用户作者
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 创建或修改判断
     * @param question
     */
    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insertSelective(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updateCount = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updateCount != 1){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);//递增 1
        questionExtMapper.incView(question);
   }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        //如歌tag为空 就返回空
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        //不为空
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTD = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTD;
    }
}
