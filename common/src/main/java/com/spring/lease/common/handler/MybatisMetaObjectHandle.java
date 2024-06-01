package com.spring.lease.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 30/5/2024 11:11 周四
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Component
public class MybatisMetaObjectHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 设置添加的时候给createTime填充值
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
        // 设置添加的时候给isdelete填充值
        this.strictInsertFill(metaObject,"isDeleted",Byte.class,(byte)0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updateTime", Date.class,new Date());
    }
}
