package com.spring.lease.web.app.service;

import com.spring.lease.model.entity.RoomInfo;
import com.spring.lease.web.app.vo.room.RoomDetailVo;
import com.spring.lease.web.app.vo.room.RoomItemVo;
import com.spring.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface RoomInfoService extends IService<RoomInfo> {


    /**
     * 分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id查询房间详细信息
     * @param id 房间id
     * @return
     */
    RoomDetailVo getDetailByid(Long id);

    /**
     * 根据公寓id查询房间列表
     * @param page
     * @param id
     * @return
     */
    IPage<RoomItemVo> pageItemByApartmentId(IPage<RoomItemVo> page, Long id);
}
