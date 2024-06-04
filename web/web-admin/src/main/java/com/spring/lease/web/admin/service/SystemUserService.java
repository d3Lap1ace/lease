package com.spring.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.spring.lease.web.admin.vo.system.user.SystemUserQueryVo;

/**
* @author liubo
* @description 针对表【system_user(员工信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUserItemVo> getPageSystemUserByQuery(IPage<SystemUserItemVo> page, SystemUserQueryVo queryVo);

    SystemUserItemVo getSystemUserById(Long id);
}
