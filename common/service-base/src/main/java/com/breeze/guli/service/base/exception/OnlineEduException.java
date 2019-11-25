package com.breeze.guli.service.base.exception;

import com.breeze.guli.common.base.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author breeze
 * @date 2019/11/25
 */
@Data
@ApiModel(value = "全局异常")
public class OnlineEduException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public OnlineEduException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public OnlineEduException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "OnlineEduException{" +
                "code=" + code +
                ", message" + this.getMessage() +
                '}';
    }
}
