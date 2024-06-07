package com.spring.lease.web.app.service;

import com.spring.lease.model.entity.ApartmentInfo;
import com.spring.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.lease.web.app.vo.apartment.ApartmentItemVo;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {


    /**
     * 根据id查询该房间所属的公寓信息
     * @param id 公寓id
     * @return
     */
    ApartmentItemVo getApartmentItemVoById(Long id);

    /**根据id查询公寓详细信息
     *
     * @param id
     * @return
     */
    ApartmentDetailVo getApartmentDetailById(Long id);
}
