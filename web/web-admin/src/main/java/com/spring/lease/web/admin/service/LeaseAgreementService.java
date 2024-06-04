package com.spring.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.LeaseAgreement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.spring.lease.web.admin.vo.agreement.AgreementVo;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    /**
     * 根据当前页和分页进行分页查询
     * @param page
     * @param queryVo
     * @return
     */
    IPage<AgreementVo> pageAgreementByQuery(IPage<AgreementVo> page, AgreementQueryVo queryVo);


    /**
     * 根据id查询租约信息
     * @param id
     * @return
     */
    AgreementVo getAgreementVoByid(Long id);
}
