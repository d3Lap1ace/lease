package com.spring.lease.common.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 30/5/2024 10:14 周四
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Configuration
@MapperScan("com.spring.lease.web.*.mapper")
public class MybatisPlusConfiguration {

}
