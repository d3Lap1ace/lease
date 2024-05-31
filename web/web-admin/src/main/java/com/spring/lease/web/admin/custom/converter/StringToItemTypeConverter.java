package com.spring.lease.web.admin.custom.converter;

import com.spring.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 31/5/2024 15:33 周五
 * @description  web字符串转换类型
 * @Version 1.0
 * From the Laplace Demon
 */


//@Component
public class StringToItemTypeConverter implements Converter<String, ItemType> {
    @Override
    public ItemType convert(String code) {
        // 获取所有枚举类型
        for (ItemType value : ItemType.values()) {
            if (value.getCode().equals(Integer.valueOf(code))) {
                return value;
            }
        }
        throw new IllegalArgumentException("code illegalArgument");
    }
}
