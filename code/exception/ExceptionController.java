package com.shu.leave.exception;

import com.shu.leave.common.ResultEntity;
import com.shu.leave.enums.ResponseCodeEnums;
import com.shu.leave.utils.BasicResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultEntity handleBusinessException(BusinessException ex) {
        log.warn("[业务异常] 出现业务异常：{}", ex.getMessage());
        return BasicResponseUtils.error(ex.getCodeMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity handleDefaultException(Exception ex) {
        //在控制台打印错误消息.
        log.error("[通用异常处理] 出现系统异常", ex);
        return BasicResponseUtils.error(ResponseCodeEnums.INTERNAL_ERROR);
    }
}
