package com.spring.lease.web.app.mapper;

import com.spring.lease.model.entity.FeeValue;
import com.spring.lease.web.app.vo.fee.FeeValueVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_value(杂项费用值表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.spring.lease.model.entity.FeeValue
*/
public interface FeeValueMapper extends BaseMapper<FeeValue> {

    /**
     * 根据公寓id查询杂费列表
     * @param apartmentId
     * @return
     */
    List<FeeValueVo> selectListByApartmentId(Long apartmentId);
}




