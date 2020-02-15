package cn.hewei.stars.service;

import cn.hewei.stars.dto.NotificationDTO;
import cn.hewei.stars.dto.PaginationDTO;
import cn.hewei.stars.model.User;
import org.springframework.stereotype.Service;

/**
 * @Author 何为
 * @Daet 2020-02-15 19:42
 * @Description
 */
public interface NotificationService {


    PaginationDTO list(Long id, Integer page, Integer size);

    Long unreadCount(Long id);

    NotificationDTO read(Long id, User user);
}
