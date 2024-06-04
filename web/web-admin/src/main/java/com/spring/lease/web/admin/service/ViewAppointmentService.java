package com.spring.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.ViewAppointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.spring.lease.web.admin.vo.appointment.AppointmentVo;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {

    /**
     * 分页查询预约信息
     * @param page
     * @param queryVo
     * @return
     */
    IPage<AppointmentVo> pageAppointmentByQuery(IPage<AppointmentVo> page, AppointmentQueryVo queryVo);
}
