package com.spring.lease.common.exception;

import com.spring.lease.common.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 1/6/2024 16:30 周六
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeaseException extends RuntimeException{
    private Integer code;
    private String message;
    public LeaseException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
