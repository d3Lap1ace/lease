package com.spring.lease.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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
    // 分页查询
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
