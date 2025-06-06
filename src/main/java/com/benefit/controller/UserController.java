package com.benefit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.UserRegisterRequest;
import com.benefit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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


    @PostMapping("/register")
    @ApiOperation("注册接口")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        //校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
}
