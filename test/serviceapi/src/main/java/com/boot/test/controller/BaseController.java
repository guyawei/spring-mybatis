package com.boot.test.controller;

import com.rnjf.bj.enums.ResultEnum;
import com.rnjf.bj.exception.ServiceException;
import com.rnjf.bj.exception.WebException;
import com.rnjf.bj.utils.IpUtil;
import com.rnjf.bj.utils.JsonUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by sunyulei on 2018/4/2.
 */
public abstract class BaseController {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected Object setSuccessModelMap() {
        return setModelMap(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getValue(), null);
    }

    protected Object setSuccessModelMap(Object result) {
        return setModelMap(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getValue(), result);
    }

    protected Object setModelMap(ResultEnum resultEnum, Object result) {
        return setModelMap(resultEnum.getCode(), resultEnum.getValue(), result);
    }

    protected Object setFailModelMap(ResultEnum resultEnum) {
        return setModelMap(resultEnum.getCode(), resultEnum.getValue(), null);
    }

    protected static Object setModelMap(int code, String msg, Object results) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("message", msg);
        if(results instanceof Map){
            resultMap.putAll((Map<String, Object>)results);
        }else{
            resultMap.put("resultObj",results);
        }

        return resultMap;
    }

    @ExceptionHandler
    public void handlException(HttpServletRequest request, HttpServletResponse response, Throwable t) throws IOException {

        response.setContentType("application/json; charset=utf-8");

        response.setHeader("Access-Control-Allow-Origin", "*");

        String url = request.getRequestURI();

        logger.error("{} 服务器开小差了, 请稍后再试 {}", url, t);

        if (t instanceof ServiceException) {
            response.getOutputStream().write(JsonUtil.bean2Json(setModelMap(HttpStatus.INTERNAL_SERVER_ERROR.value(), t.getMessage(), null)).getBytes());
        } else if (t instanceof WebException) {
            WebException we = (WebException) t;
            response.getOutputStream().write(JsonUtil.bean2Json(setModelMap(we.getStatus().value(), we.getMessage(), null)).getBytes());
        } else if (t instanceof IllegalArgumentException) {
            IllegalArgumentException ie = (IllegalArgumentException) t;
            response.getOutputStream().write(JsonUtil.bean2Json(setModelMap(HttpStatus.BAD_REQUEST.value(), ie.getMessage(), null)).getBytes());
        } else {
            response.getOutputStream().write(JsonUtil.bean2Json(setModelMap(HttpStatus.INTERNAL_SERVER_ERROR.value(), t.getMessage(), null)).getBytes());
        }
    }


    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public String getIpAddress()throws Exception{
        return IpUtil.getIpAddress(this.getRequest());
    }


}
