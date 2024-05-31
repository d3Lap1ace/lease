package com.spring.lease.web.admin.custom.config;

import com.spring.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import com.spring.lease.web.admin.custom.converter.StringToItemTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 31/5/2024 15:35 周五
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(this.stringToBaseEnumConverterFactory);
    }
}
