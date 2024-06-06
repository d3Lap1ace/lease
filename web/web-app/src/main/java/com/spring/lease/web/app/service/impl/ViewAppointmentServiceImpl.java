package com.spring.lease.web.app.service.impl;

import com.spring.lease.model.entity.ViewAppointment;
import com.spring.lease.web.app.mapper.ViewAppointmentMapper;
import com.spring.lease.web.app.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.lease.web.app.vo.appointment.AppointmentItemVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {


    @Resource
    private ViewAppointmentMapper viewAppointmentMapper;

    @Override
    public List<AppointmentItemVo> getAppointmentItemByUserId(Long id) {
        return viewAppointmentMapper.getAppointmentItemByUserId(id);
    }
}




