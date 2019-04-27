package com.su.handler;

import com.su.exception.SellException;
import com.su.exception.SellerAuthException;
import com.su.util.ResultVOUtil;
import com.su.viewobject.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于处理异常的异常处理器：
 * 注意：要使用@ControllerAdvice注解
 */
@ControllerAdvice
public class SellerExceptionHandler {

    /**
     * 拦截登录异常：SellerAuthException
     * 由AOP中的SellerAuthAspect中的Advice抛出
     * 跳转到登录页面
     * @ResponseStatus(HttpStatus.FORBIDDEN): 表示会更改返回的Http的响应状态码code
     * */
    @ExceptionHandler(SellerAuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerSellerAuthException(){
        return new ModelAndView("info/login");
    }

    /**
     * 拦截SellException异常，并进行相应的处理
     * */
    @ExceptionHandler(SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
