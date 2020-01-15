package com.ccx.jsj.handler.exceptionHandler;

import com.ccx.jsj.emun.ResultEnum;
import com.ccx.jsj.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.ccx.jsj.emun.ResultEnum.ParamErrResult;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 验证异常
     *
     * @param req
     * @param e
     * @return
     * @throws MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) throws MethodArgumentNotValidException {

        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMesssage = new StringBuilder();
        errorMesssage.append("Invalid Request:\n");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage.append(fieldError.getDefaultMessage()).append("\n");
        }
        logger.warn("MethodArgumentNotValidException:{}", errorMesssage);

        return R.error(ParamErrResult.getCode(), errorMesssage.toString());
    }


    /**
     * 无权限
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public R handleAccessDeniedException(HttpServletRequest req, AccessDeniedException e) {
        logger.error(e.getMessage(), e);
        return R.error(ResultEnum.AccessDeniedResult.getCode(), ResultEnum.AccessDeniedResult.getMsg());
    }

    /**
     * 全局异常
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R handleException(HttpServletRequest req, Exception e) {

        logger.error(e.getMessage(), e);
        return R.error(ResultEnum.FailResult.getCode(), ResultEnum.FailResult.getMsg());
    }

}
