package cn.hewei.stars.advice;

import cn.hewei.stars.dto.ResultDTO;
import cn.hewei.stars.exception.CustomizeErrorCode;
import cn.hewei.stars.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author 何为
 * @Daet 2020-02-11 23:47
 * @Description
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    /**
     * @param ex
     * @return 返回 ModelAndView
     */
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex,
                  Model model,
                  HttpServletRequest request,
                  HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            //返回JSON
            if (ex instanceof CustomizeException) {
                //此语句获取的是 用户访问question页面的错误返回
                resultDTO = ResultDTO.errorOf((CustomizeException)ex);
            }else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
            }
            return null;
        }else {
            //错误页面跳转
            if (ex instanceof CustomizeException) {
                //此语句获取的是 用户访问question页面的错误返回
                model.addAttribute("messages",ex.getMessage());
            }else {
                model.addAttribute("messages",CustomizeErrorCode.SYS_ERROR.getMassage());
            }
            //返回到 error.html
            return new ModelAndView("error");
        }

    }

}
