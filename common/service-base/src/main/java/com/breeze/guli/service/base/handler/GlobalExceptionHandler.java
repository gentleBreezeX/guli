package com.breeze.guli.service.base.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.breeze.guli.common.base.result.R;
import com.breeze.guli.common.base.result.ResultCodeEnum;
import com.breeze.guli.common.base.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author breeze
 * @date 2019/11/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e){
        log.error(ResultCodeEnum.JSON_PARSE_ERROR.toString());
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(MyBatisSystemException.class)
    @ResponseBody
    public R error(MyBatisSystemException e){
        log.error(ResultCodeEnum.BAD_SQL_GRAMMAR.toString());
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }
}