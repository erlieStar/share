package com.javashitang.blog.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobleExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ServerResponse handlerException(Exception e) {
        log.error("occur error", e);
        return ServerResponse.errorMsg("系统发生异常，请联系管理员微信，zztierlie");
    }
}
