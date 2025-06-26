package com.benefit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.List;
import java.util.stream.Collectors;

import static com.benefit.constant.UserConstant.USER_LOGIN_STATUS;


/**
 * @author Allen
 * @date 2025/6/6 14:53
 */
@RestController
@RequestMapping("/user")
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
        log.info("user login result:{}", "userName: " + user.getAccount());
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

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @ApiOperation("获取当前用户接口")
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }


    /**
     * 查询用户
     * @param account
     * @param request
     * @return
     */
    @ApiOperation("查询用户接口")
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String account, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            queryWrapper.like("account", account);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 删除用户
     * @param user
     * @param request
     * @return
     */
    @ApiOperation("删除接口")
    @PostMapping("/delete")
    public BaseResponse<Integer> deleteUser(@RequestBody User user, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        Long id = user.getId();
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        int deletedId = userService.updateUserById(user,loginUser,request);
        return ResultUtils.success(deletedId);
    }

    @ApiOperation("查询用户是否已注册")
    @PostMapping("/isRegister")
    public BaseResponse isRegister(@RequestBody String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            queryWrapper.like("account", account);
        }
        List<User> userList = userService.list(queryWrapper);
        if(userList == null || userList.isEmpty()) {
            //没查到,可以注册
            return ResultUtils.success(new User());
        }
        return ResultUtils.error(ErrorCode.IS_REGISTER);
    }
}
