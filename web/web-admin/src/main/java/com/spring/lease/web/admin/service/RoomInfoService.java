package com.spring.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.RoomInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.lease.web.admin.vo.room.RoomDetailVo;
import com.spring.lease.web.admin.vo.room.RoomItemVo;
import com.spring.lease.web.admin.vo.room.RoomQueryVo;
import com.spring.lease.web.admin.vo.room.RoomSubmitVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {
    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);
    void removeRoomById(Long id);
    RoomDetailVo getRoomDetailById(Long id);
}
