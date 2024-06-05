package com.spring.lease.web.admin.controller.login;


import com.spring.lease.common.context.LoginUserContext;
import com.spring.lease.common.result.Result;
import com.spring.lease.common.utils.JwtUtil;
import com.spring.lease.web.admin.service.LoginService;
import com.spring.lease.web.admin.vo.login.CaptchaVo;
import com.spring.lease.web.admin.vo.login.LoginVo;
import com.spring.lease.web.admin.vo.system.user.SystemUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captcha = loginService.getCaptcha();
        return Result.ok(captcha);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token = loginService.login(loginVo);
        return Result.ok(token);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        SystemUserInfoVo user = loginService.getSystemUserInfoById(LoginUserContext.getLoginUser().getUserId());
        return Result.ok(user);
    }
}