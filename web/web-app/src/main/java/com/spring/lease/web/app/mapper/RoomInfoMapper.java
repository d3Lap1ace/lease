package com.spring.lease.web.app.mapper;

import com.spring.lease.model.entity.RoomInfo;
import com.spring.lease.web.app.vo.room.RoomItemVo;
import com.spring.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.spring.lease.model.entity.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {


    /**
     * 分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id查询房间信息
     * @param id
     * @return
     */
    RoomInfo selectRoomById(Long id);

    /**
     * 根据公寓id查询最少的房间价格
     * @param id
     * @return
     */
    BigDecimal selectMinRentByApartmentId(Long id);


    /**
     * 根据公寓id 查询房间列表
     * @param page
     * @param id
     * @return
     */
    IPage<RoomItemVo> pageItemByApartmentId(IPage<RoomItemVo> page, Long id);
}