package com.spring.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.ApartmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.spring.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.spring.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.spring.lease.web.admin.vo.apartment.ApartmentSubmitVo;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {
    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);
    void deleteApartmentById(Long id);
    ApartmentDetailVo getApartmentDetilByid(Long id);
    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);
}
