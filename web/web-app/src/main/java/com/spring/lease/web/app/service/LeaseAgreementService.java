package com.spring.lease.web.app.service;

import com.spring.lease.model.entity.LeaseAgreement;
import com.spring.lease.web.app.vo.agreement.AgreementDetailVo;
import com.spring.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    /**
     * 获取个人租约基本信息列表
     * @param username 手机号
     * @return
     */
    List<AgreementItemVo> getListAgreementByUserId(String username);

    /**
     * 根据id获取租约详细信息
     * @param id
     * @return
     */
    AgreementDetailVo getAgreementDetailById(Long id);
}
