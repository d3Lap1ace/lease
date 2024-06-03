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
    /**
     * 保存和更新公寓信息
     * @param apartmentSubmitVo 封装了公寓信息
     */
    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);

    /**
     *
     * @param page 封装了当前页和每页显示的条数的page对象
     * @param queryVo  封装了省、市、区id的查询条件的Vo对象
     * @return
     */
    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    /**
     * 根据公寓id查询公寓信息
     * @param id
     * @return
     */
    ApartmentDetailVo getApartmentDetilByid(Long id);


    /**
     * 根据公寓id删除公寓信息
     * @param id
     */
    void deleteApartmentById(Long id);



}
