package cn.hewei.stars.interceptor;

import cn.hewei.stars.mapper.UserMapper;
import cn.hewei.stars.model.User;
import cn.hewei.stars.model.UserExample;
import cn.hewei.stars.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author 何为
 * @Daet 2020-02-08 13:07
 * @Description
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取浏览器所有cookie
        Cookie[] cookies = request.getCookies();
        //预防浏览器没有cookie
        if (cookies!=null && cookies.length!=0){
            //遍历所有cookie
            for (Cookie cookie : cookies) {
                //如果浏览器中 cookie名 是 token
                if ("token".equals(cookie.getName())){
                    //获取value
                    String token = cookie.getValue();
                    //用token的value查询数据库中是否存在
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    //如果存在
                    if (users.size()!=0){
                        //在导航栏右上角显示用户信息
                        request.getSession().setAttribute("user",users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadMessage",unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
