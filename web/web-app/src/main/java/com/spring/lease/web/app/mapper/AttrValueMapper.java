package com.spring.lease.web.app.mapper;

import com.spring.lease.model.entity.AttrValue;
import com.spring.lease.web.app.vo.attr.AttrValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_value(房间基本属性值表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.spring.lease.model.entity.AttrValue
*/
public interface AttrValueMapper extends BaseMapper<AttrValue> {

    /**
     * 查看房间的基本属性
     * @param id
     * @return
     */
    List<AttrValueVo> selectListByRoomId(Long id);
}




