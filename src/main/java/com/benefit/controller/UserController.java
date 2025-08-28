package com.benefit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benefit.common.BaseResponse;
import com.benefit.common.ResultUtils;
import com.benefit.exception.BusinessException;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.request.UserLoginRequest;
import com.benefit.request.UserRegisterRequest;
import com.benefit.service.BenefitProductService;
import com.benefit.service.OrderMainService;
import com.benefit.service.UserService;
import com.benefit.vo.DashVo;
import com.benefit.vo.UserDashVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

    @Resource
    private BenefitProductService benefitProductService;

    @Resource
    private OrderMainService orderMainService;


    /**
     * 注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册接口")
    public BaseResponse<User> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        //校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String account = userRegisterRequest.getAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            return null;
        }
        User user = userService.userRegister(account, password, checkPassword);
        return ResultUtils.success(user);
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
        log.info("user login result:{}", "userName: " + user);
        if (ObjectUtils.isEmpty(user)){
            return ResultUtils.error(ErrorCode.NOT_ALLOWED_LOGIN);
        }
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
* @Description: 删除接口
* @Param: [user, request]
* @Return: com.benefit.common.BaseResponse<java.lang.Integer>
* @Author: Allen
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

    /**
    * @Description: 查询用户名是否已注册
    * @Param: [account]
    * @Return: com.benefit.common.BaseResponse
    * @Author: Allen
    */
    @ApiOperation("查询用户名是否已注册")
    @GetMapping("/isRegister")
    public BaseResponse isRegister(@RequestParam String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(account)) {
            queryWrapper.eq("account", account);
        }
        User user = userService.getOne(queryWrapper);
        if(user == null) {
            //没查到,可以注册
            return ResultUtils.success(false);
        }
        return ResultUtils.error(ErrorCode.IS_REGISTER);
    }


    /**
    * @Description: 数据面板
    * @Param: [request]
    * @Return: com.benefit.common.BaseResponse<java.util.List<com.benefit.vo.UserDashVo>>
    * @Author: Allen
    */
    @ApiOperation("dashboard")
    @GetMapping("/getUserRegisterCount")
    public BaseResponse<List<UserDashVo>> getUserRegisterCount(HttpServletRequest request) {

        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        List<UserDashVo> vo = userService.getUserRegisterCount();

        return ResultUtils.success(vo);
    }

    /**
    * @Description: admin用户登录
    * @Param: [userLoginRequest, request]
    * @Return: com.benefit.common.BaseResponse<com.benefit.model.entity.User>
    * @Author: Allen
    */
    @ApiOperation("Admin登录接口")
    @PostMapping("/adminLogin")
    public BaseResponse<User> adminLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String account = userLoginRequest.getAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(account, password)) {
            return ResultUtils.error(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        User user = userService.adminLogin(account, password, request);
        log.info("user login result:{}", "userName: " + user);
        if (ObjectUtils.isEmpty(user)){
            return ResultUtils.error(ErrorCode.NOT_ALLOWED_LOGIN);
        }
        return ResultUtils.success(user);
    }


    @ApiOperation("dashboard获取总数")
    @PostMapping("/getCount")
    public BaseResponse<DashVo> getCount(HttpServletRequest request){
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Integer userCount = userService.getUserCount();
        Integer productCount = benefitProductService.getProductCount();
        Map<String, Object> map = orderMainService.getOrderMainCount();
        Object orderMainCount = map.get("orderMainCount");
        Object totalPoints = map.get("totalPoints");

        DashVo vo = new DashVo();
        vo.setUserCount(userCount);
        vo.setProductCount(productCount);
        vo.setOrderMainCount(orderMainCount);
        vo.setOrderPointsCount(totalPoints);

        return ResultUtils.success(vo);
    }
}
