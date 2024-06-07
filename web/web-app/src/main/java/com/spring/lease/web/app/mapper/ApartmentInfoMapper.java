package com.spring.lease.web.app.mapper;

import com.spring.lease.model.entity.ApartmentInfo;
import com.spring.lease.web.app.vo.apartment.ApartmentItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.spring.lease.model.entity.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {


    /**
     * 根据id查询公寓详细信息
     * @param id
     * @return
     */
    ApartmentInfo selectApartmentById(Long id);
}




