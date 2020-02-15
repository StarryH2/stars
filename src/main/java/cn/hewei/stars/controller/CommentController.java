package cn.hewei.stars.controller;

import cn.hewei.stars.dto.CommentCreateDTO;
import cn.hewei.stars.dto.CommentDTO;
import cn.hewei.stars.dto.ResultDTO;
import cn.hewei.stars.enums.CommentTypeEnum;
import cn.hewei.stars.exception.CustomizeErrorCode;
import cn.hewei.stars.model.Comment;
import cn.hewei.stars.model.User;
import cn.hewei.stars.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Author 何为
 * @Daet 2020-02-12 12:26
 * @Description
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        Comment commnet = new Comment();
        commnet.setParentId(commentCreateDTO.getParentId());
        commnet.setContent(commentCreateDTO.getContent());
        commnet.setType(commentCreateDTO.getType());
        commnet.setGmtModified(System.currentTimeMillis());
        commnet.setGmtCreate(System.currentTimeMillis());
        commnet.setCommentator(user.getId());
        commnet.setLikeCount(0L);
        commentService.insert(commnet,user);
        return ResultDTO.okOf();
    }

    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<List> comments(@PathVariable("id")Long id){
        List<CommentDTO> CommentDTOS = commentService.findByTargetId(id, CommentTypeEnum.COMMENT);

        return ResultDTO.okOf(CommentDTOS);
    }

}
