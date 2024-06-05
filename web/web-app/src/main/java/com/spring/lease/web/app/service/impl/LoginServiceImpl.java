package com.spring.lease.web.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.lease.common.constant.RedisConstant;
import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.ResultCodeEnum;
import com.spring.lease.common.utils.JwtUtil;
import com.spring.lease.model.entity.UserInfo;
import com.spring.lease.model.enums.BaseStatus;
import com.spring.lease.web.app.service.LoginService;
import com.spring.lease.web.app.service.UserInfoService;
import com.spring.lease.web.app.utils.SMSUtils;
import com.spring.lease.web.app.vo.user.LoginVo;
import com.spring.lease.web.app.vo.user.UserInfoVo;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired // 放入到redis中
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void sendCode(String phone) {
        String code = RandomStringUtils.randomNumeric(4);
        // 调用SMSUtils向手机号发送验证码
        System.out.println("code = " + code);
//        SMSUtils.sendMessage(phone,code);
        // 设置保存验证码的key
        String key = "app:login:"+phone;
        // 将验证码保存到Redis中,并设置有效时间为5分钟
        stringRedisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);
    }

    @Override
    public String login(LoginVo loginVo) {
        // 1 判断手机和验证码是否为空
        if (!StringUtils.hasText(loginVo.getPhone())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }

        if (!StringUtils.hasText(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }
        // 2 校验验证码
        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getCode();
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }

        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }
        //3.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(5));
            userInfoService.save(userInfo);
        }

        //4.判断用户是否被禁
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }

        //5.创建并返回TOKEN
        return JwtUtil.createToken(userInfo.getId(), loginVo.getPhone());
    }

    /**
     * 实现 获取登录用户信息
     * @param id
     * @return
     */
    @Override
    public UserInfoVo getUserInfoId(Long id) {
        UserInfo userInfo = userInfoService.getById(id);
        return new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
    }

}
