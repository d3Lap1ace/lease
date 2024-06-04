package com.spring.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.ApartmentInfo;
import com.spring.lease.model.entity.LeaseAgreement;
import com.spring.lease.web.admin.mapper.ApartmentInfoMapper;
import com.spring.lease.web.admin.mapper.LeaseAgreementMapper;
import com.spring.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.spring.lease.web.admin.vo.agreement.AgreementVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Resource
    private LeaseAgreementMapper leaseAgreementMapper;

    /**
     * 实现 根据条件分页查询租约列表
     * @param page
     * @param queryVo
     * @return
     */
    @Override
    public IPage<AgreementVo> pageAgreementByQuery(IPage<AgreementVo> page, AgreementQueryVo queryVo) {
        return leaseAgreementMapper.pageAgreementByQuery(page,queryVo);
    }

    /**
     * 实现 根据id查询租约列表
     * @param id
     * @return
     */
    @Override
    public AgreementVo getAgreementVoByid(Long id) {
        return leaseAgreementMapper.getAgreementDetailByid(id);
    }
}




