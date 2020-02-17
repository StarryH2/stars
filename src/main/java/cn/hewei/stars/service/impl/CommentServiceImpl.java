package cn.hewei.stars.service.impl;

import cn.hewei.stars.dto.CommentDTO;
import cn.hewei.stars.enums.CommentTypeEnum;
import cn.hewei.stars.enums.NotificationStatusEnum;
import cn.hewei.stars.enums.NotificationTypeEnum;
import cn.hewei.stars.exception.CustomizeErrorCode;
import cn.hewei.stars.exception.CustomizeException;
import cn.hewei.stars.mapper.*;
import cn.hewei.stars.model.*;
import cn.hewei.stars.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 何为
 * @Daet 2020-02-12 13:41
 * @Description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;


    @Override
    @Transactional//整个方法体全部加上事务
    public void insert(Comment commnet,User commentator) {

        if (commnet.getParentId() == null || commnet.getParentId() == 0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (commnet.getType() == null || !CommentTypeEnum.isExist(commnet.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (commnet.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(commnet.getParentId());
            if (dbComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(commnet);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(commnet.getParentId());
            parentComment.setCommentCount(1);
            //累加评论数
            commentExtMapper.incCommentCount(parentComment);

            //评论提示
            //创建通知
            createNotify(commnet, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT,question.getId());
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(commnet.getParentId());
            if (question == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commnet.setCommentCount(0);
            commentMapper.insert(commnet);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //创建通知
            createNotify(commnet,question.getCreator(),commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }
    }

    private void createNotify(Comment commnet, Long receiver,String notifierName, String outerTitle,  NotificationTypeEnum notificationType,Long outerId) {
        //防止自己通知自己
        if (receiver.equals(commnet.getCommentator()) || receiver == commnet.getCommentator()){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(commnet.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    @Override
    public List<CommentDTO> findByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());

        //倒序排序
        commentExample.setOrderByClause("GMT_CREATE DESC");

        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size()==0){
            return new ArrayList<>();
        }

        List<Long> userIds = new ArrayList<>();
        //获取评论数根据用户ID不重复-----获取去重的评论人
        Set<Long> commentators = comments.stream()
                .map(comment -> comment.getCommentator())//用户Id
                .collect(Collectors.toSet());
        //将Set集合放入List中-----将set转换为UserId
        userIds.addAll(commentators);

        //获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap
                (user -> user.getId(), user -> user));

        //转换Comment 为 CommentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());


        return commentDTOS;
    }
}
