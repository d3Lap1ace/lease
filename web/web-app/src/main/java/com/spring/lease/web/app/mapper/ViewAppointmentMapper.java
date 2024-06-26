package com.spring.lease.web.app.mapper;

import com.spring.lease.model.entity.ViewAppointment;
import com.spring.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.spring.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.spring.lease.model.entity.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {


    /** 查询个人预约看房列表
     * @param id
     * @return
     */
    List<AppointmentItemVo> getAppointmentItemByUserId(Long id);



}




