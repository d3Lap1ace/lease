package com.spring.lease.web.app.service;

import com.spring.lease.web.app.vo.user.LoginVo;
import com.spring.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {


    void sendCode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo getUserInfoId(Long id);
}
