package com.shu.leave.exception;

import com.shu.leave.enums.ResponseCodeEnums;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException {

    private ResponseCodeEnums codeMsg;

    public BusinessException(ResponseCodeEnums codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }
}