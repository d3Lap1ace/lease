package com.spring.lease.web.admin.mapper;

import com.spring.lease.model.entity.GraphInfo;
import com.spring.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.lease.web.admin.vo.graph.GraphVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【graph_info(图片信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.spring.lease.model.GraphInfo
*/
public interface GraphInfoMapper extends BaseMapper<GraphInfo> {
    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long itemId);
    List<GraphVo> selectRoomListByItemTypeAndId(ItemType itemType, Long itemId);
}




