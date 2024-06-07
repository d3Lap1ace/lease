package com.spring.lease.web.app.mapper;

import com.spring.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_term(租期)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.spring.lease.model.entity.LeaseTerm
*/
public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    /**
     * 根据房间id查询租期信息
     * @param id
     * @return
     */
    List<LeaseTerm> selectListByRoomId(Long id);


    LeaseTerm selectLeaseTermById(Long leaseTermId);
}




