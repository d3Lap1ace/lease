package com.spring.lease.web.admin.service;

import com.spring.lease.web.admin.vo.login.CaptchaVo;
import com.spring.lease.web.admin.vo.login.LoginVo;
import com.spring.lease.web.admin.vo.system.user.SystemUserInfoVo;


public interface LoginService {

    /**
     * 获取图像验证码
     * @return
     */
    CaptchaVo getCaptcha();

    /**
     * 获得 token
     * @param loginVo
     * @return
     */
    String login(LoginVo loginVo);

    /**
     * 获取个人登录信息
     * @param userId
     * @return
     */
    SystemUserInfoVo getSystemUserInfoById(Long userId);
}
