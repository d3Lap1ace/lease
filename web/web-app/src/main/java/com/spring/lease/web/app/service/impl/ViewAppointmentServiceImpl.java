package com.spring.lease.web.app.service.impl;

import com.spring.lease.model.entity.ViewAppointment;
import com.spring.lease.web.app.mapper.ApartmentInfoMapper;
import com.spring.lease.web.app.mapper.ViewAppointmentMapper;
import com.spring.lease.web.app.service.ApartmentInfoService;
import com.spring.lease.web.app.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.spring.lease.web.app.vo.apartment.ApartmentItemVo;
import com.spring.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.spring.lease.web.app.vo.appointment.AppointmentItemVo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ApartmentInfoService apartmentInfoService;

    /**
     * 查询个人预约看房列表
     * @param id
     * @return
     */
    @Override
    public List<AppointmentItemVo> getAppointmentItemByUserId(Long id) {
        return viewAppointmentMapper.getAppointmentItemByUserId(id);
    }

    /**
     * 根据ID查询预约详情信息
     * @param id
     * @return
     */
    @Override
    public AppointmentDetailVo getAppointmentDetailVoById(Long id) {
        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        if(viewAppointment == null) return  null;
        ApartmentItemVo apartmentItemVo = apartmentInfoService.getApartmentItemVoById(viewAppointment.getApartmentId());


        AppointmentDetailVo detailVo = new AppointmentDetailVo();
        BeanUtils.copyProperties(viewAppointment, detailVo);
        detailVo.setApartmentItemVo(apartmentItemVo);
        return detailVo;
    }
}




