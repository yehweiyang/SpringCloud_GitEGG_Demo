package com.weiyang.platform.boot.common.advice;


import com.weiyang.platform.boot.common.base.Result;
import com.weiyang.platform.boot.common.enums.ResultCodeEnum;
import com.weiyang.platform.boot.common.exception.BusinessException;
import com.weiyang.platform.boot.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GitEggControllerAdvice {

    /**
     * 服務名
     */
    @Value("${spring.application.name}")
    private String serverName;

    /**
     * 微服務系統標識
     */
    private String errorSystem;

    @PostConstruct
    public void init() {
        this.errorSystem = new StringBuffer()
                .append(this.serverName)
                .append(": ").toString();
    }

    /**
     * 應用到所有@RequestMapping註解方法，在其執行之前初始化資料繫結器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    /**
     * 把值繫結到Model中，使全域@RequestMapping可以獲取到該值
     */
    @ModelAttribute
    public void addAttributes(Model model) {

    }

    /**
     * 全域異常捕捉處理
     */
    @ExceptionHandler(value = {Exception.class})
    public Result handlerException(Exception exception, HttpServletRequest request) {
        log.error("請求路徑uri={},系統內部出現異常:{}", request.getRequestURI(), exception);
        Result result = Result.error(ResultCodeEnum.ERROR, errorSystem + exception.toString());
        return result;
    }

    /**
     * 非法請求異常
     */
    @ExceptionHandler(value = {
            HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            NoHandlerFoundException.class,
            MissingPathVariableException.class,
            HttpMessageNotReadableException.class
    })
    public Result handlerSpringAOPException(Exception exception) {
        Result result = Result.error(ResultCodeEnum.ILLEGAL_REQUEST, errorSystem + exception.getMessage());
        return result;
    }

    /**
     * 非法請求異常-參數類型不匹配
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result handlerSpringAOPException(MethodArgumentTypeMismatchException exception) {
        Result result = Result.error(ResultCodeEnum.PARAM_TYPE_MISMATCH, errorSystem + exception.getMessage());
        return result;
    }

    /**
     * 非法請求-參數校驗
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        //獲取異常欄位及對應的異常資訊
        StringBuffer stringBuffer = new StringBuffer();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(t -> t.getField() + "=>" + t.getDefaultMessage() + " ")
                .forEach(e -> stringBuffer.append(e));
        String errorMessage = stringBuffer.toString();
        Result result = Result.error(ResultCodeEnum.PARAM_VALID_ERROR, errorSystem + errorMessage);
        return result;
    }

    /**
     * 非法請求異常-參數校驗
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Result handlerConstraintViolationException(ConstraintViolationException constraintViolationException) {
        String errorMessage = constraintViolationException.getLocalizedMessage();
        Result result = Result.error(ResultCodeEnum.PARAM_VALID_ERROR, errorSystem + errorMessage);
        return result;
    }

    /**
     * 自訂業務異常-BusinessException
     */
    @ExceptionHandler(value = {BusinessException.class})
    public Result handlerCustomException(BusinessException exception) {
        String errorMessage = exception.getMsg();
        Result result = Result.error(exception.getCode(), errorSystem + errorMessage);
        return result;
    }

    /**
     * 自訂系統異常-SystemException
     */
    @ExceptionHandler(value = {SystemException.class})
    public Result handlerCustomException(SystemException exception) {
        String errorMessage = exception.getMsg() + "自訂系統異常-SystemException..... TOTALLY ERROR";
        Result result = Result.error(exception.getCode(), errorSystem + errorMessage);
        return result;
    }

}