package cn.hewei.stars.service.impl;

import cn.hewei.stars.dto.NotificationDTO;
import cn.hewei.stars.dto.PaginationDTO;
import cn.hewei.stars.dto.QuestionDTO;
import cn.hewei.stars.enums.NotificationStatusEnum;
import cn.hewei.stars.enums.NotificationTypeEnum;
import cn.hewei.stars.exception.CustomizeErrorCode;
import cn.hewei.stars.exception.CustomizeException;
import cn.hewei.stars.mapper.NotificationMapper;
import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.*;
import cn.hewei.stars.service.NotificationService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author 何为
 * @Daet 2020-02-15 19:43
 * @Description
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        //分页数据
        PaginationDTO paginationDTO = new PaginationDTO();
        //所有的数量
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount =(int) notificationMapper.countByExample(notificationExample);
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

        //查询社区用户的发现+分页
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper
                .selectByExampleWithRowbounds
                        (example, new RowBounds(offset, size));
        if (notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    @Override
    public Long unreadCount(Long userID) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userID)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);

    }

    @Override
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
