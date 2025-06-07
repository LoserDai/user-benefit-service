package com.benefit.controller;

import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.UserLoginRequest;
import com.benefit.request.UserRegisterRequest;
import com.benefit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author Allen
 * @date 2025/6/6 14:53
 */
@RestController
@RequestMapping("/user")
//支持跨域请求后端
@CrossOrigin(origins = {"http://localhost:3000"})
@Api(tags = "用户操作入口")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册接口")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        //校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String account = userRegisterRequest.getAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(password, password, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(account, password, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     * 更新用户信息
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改接口")
    public BaseResponse<Integer> userUpdate(@RequestBody User user, HttpServletRequest request) {
        if (user == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User loginUser = userService.getLoginUser(request);
        int result = userService.updateUser(user, loginUser);
        return ResultUtils.success(result);
    }

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(account, password)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(account, password, request);
        return ResultUtils.success(user);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
}
