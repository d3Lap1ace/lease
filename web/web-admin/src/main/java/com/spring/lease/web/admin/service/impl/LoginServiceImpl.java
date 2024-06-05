package com.spring.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.lease.common.constant.RedisConstant;
import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.ResultCodeEnum;
import com.spring.lease.common.utils.JwtUtil;
import com.spring.lease.model.entity.SystemUser;
import com.spring.lease.model.enums.BaseStatus;
import com.spring.lease.web.admin.mapper.SystemUserMapper;
import com.spring.lease.web.admin.service.LoginService;
import com.spring.lease.web.admin.vo.login.CaptchaVo;
import com.spring.lease.web.admin.vo.login.LoginVo;
import com.spring.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SystemUserMapper systemUserMapper;


    /**
     * 实现 获取验证码
     * @return
     */
    @Override
    public CaptchaVo getCaptcha() {
        // 创建specCaptcha对象
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        // 设置验证码类型
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        // 获取小写验证码
        String code = specCaptcha.text().toLowerCase();
        // 设置向redis中保存验证码的key
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
        // 获取验证码图片地址
        String image = specCaptcha.toBase64();
        // 将验证码放入redis 并设置有效时长3分钟
        stringRedisTemplate.opsForValue().set(key, code, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        // 将验证码返回给调用者
        return new CaptchaVo(image, key);
    }

    /**
     * 实现 token
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        //1.判断是否输入了验证码
        if (!StringUtils.hasText(loginVo.getCaptchaCode())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        //2.校验验证码
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey()); // 获得验证码
        if (code == null) { // 验证码过期
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        // 验证码输入错误
        if (!code.equals(loginVo.getCaptchaCode().toLowerCase())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        //3.校验用户是否存在
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, loginVo.getUsername());
        SystemUser systemUser = systemUserMapper.selectOne(queryWrapper);
        if (systemUser == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        //4.校验用户是否被禁
        if (systemUser.getStatus() == BaseStatus.DISABLE) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        //5.校验用户密码
        if (!systemUser.getPassword().equals(DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes()))) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //6.创建并返回TOKEN
        String token = JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
        return token;
    }

    @Override
    public SystemUserInfoVo getSystemUserInfoById(Long userId) {
        SystemUser systemUser = systemUserMapper.selectById(userId);
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }
}
