package com.spring.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.model.entity.SystemUser;
import com.spring.lease.web.admin.mapper.SystemUserMapper;
import com.spring.lease.web.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.spring.lease.web.admin.vo.system.user.SystemUserQueryVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;
    @Override
    public IPage<SystemUserItemVo> getPageSystemUserByQuery(IPage<SystemUserItemVo> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.getpageSystemUserByQuery(page, queryVo);
    }

    @Override
    public SystemUserItemVo getSystemUserById(Long id) {
        return systemUserMapper.getSystemUserById(id);
    }
}




