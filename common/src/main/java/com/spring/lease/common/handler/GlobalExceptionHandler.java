package com.spring.lease.common.handler;

import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.Result;
import com.spring.lease.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 1/6/2024 16:30 周六
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@RestControllerAdvice  // 异常注解
public class GlobalExceptionHandler {
    // 处理异常的方法
    @ExceptionHandler(Exception.class)
    public Result doResolveException(Exception e){
        e.printStackTrace(); // 打印栈踪迹
        return Result.fail();
    }

    // 处理自定义异常
    @ExceptionHandler(LeaseException.class)
    public Result doResolveLeaseException(LeaseException e){
        e.printStackTrace();
        return Result.lease(e.getCode(),e.getMessage());
    }
}
