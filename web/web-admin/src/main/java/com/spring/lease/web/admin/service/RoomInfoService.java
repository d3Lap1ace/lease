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
    /**
     *保存或更新房间信息
     * @param roomSubmitVo
     */
    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);


    /**
     * 根据当前页和页表查询房间列表
     * @param page 当前也
     * @param queryVo 页表
     * @return
     */
    IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    /**
     * 根据id查询房间详细信息
     * @param id
     * @return
     */
    RoomDetailVo getRoomDetailById(Long id);

    /**
     * 根据id删除房间
     * @param id
     */
    void removeRoomById(Long id);
}
