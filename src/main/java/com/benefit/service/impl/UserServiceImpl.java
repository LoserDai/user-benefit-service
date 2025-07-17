package com.benefit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.constant.UserConstant;
import com.benefit.exception.BusinessException;
import com.benefit.mapper.BenefitPointsMapper;
import com.benefit.mapper.UserMapper;
import com.benefit.model.entity.BenefitPoints;
import com.benefit.model.entity.User;
import com.benefit.model.enums.ErrorCode;
import com.benefit.model.enums.Status;
import com.benefit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.benefit.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author Allen
 * @date 2025/6/6 15:39
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private BenefitPointsMapper pointsMapper;

    //盐值
    private static final String SALT = "Allen";

    /**
     * 注册
     * @param account   用户账户
     * @param password  用户密码
     * @param checkPassword 校验密码
     * @return
     */
    @Override
    public User userRegister(String account, String password, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (account.length() < 4 || account.length() > 17) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号请保持在5-17之间");
        }
        if (password.length() < 8 || checkPassword.length() < 8 || password.length() > 18 || checkPassword.length() > 18) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码请保持在7-18之间");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);
        if (matcher.find()) {
            return null;
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            return null;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setAccount(account);
        user.setPassword(encryptPassword);
        user.setStatus(Status.INACTIVE);
        user.setCreatedBy("SYSTEM");
        user.setUpdatedBy("SYSTEM");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return null;
        }
        //注册后给他创建账户
        BenefitPoints points = new BenefitPoints();
        points.setUserId(user.getId());
        int insert = pointsMapper.insert(points);
        if (insert <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return user;
    }

    @Override
    public User userLogin(String account, String password, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(account, password)) {
            return null;
        }
        if (account.length() < 4 || account.length() > 16) {
            return null;
        }
        if (password.length() < 8 || password.length() > 18) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //判断用户是否已注销
        if (Status.DELETED == user.getStatus() || Status.EXPIRED == user.getStatus()){
            log.info("user not allow to login,status is {}",user.getStatus());
            return null;
        }
        // 用户不存在
        if (ObjectUtils.isEmpty(user)) {
            log.info("user login failed, account cannot match password");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);
        return safetyUser;
    }

    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setAccount(originUser.getAccount());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setStatus(originUser.getStatus());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        safetyUser.setCreatedBy(originUser.getCreatedBy());
        safetyUser.setUpdatedBy(originUser.getUpdatedBy());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }

    @Override
    public List<User> searchUsersByTags(List<String> tagNameList) {
        return null;
    }

    @Override
    public int updateUser(User user, User loginUser) {
        //被修改的用户id
        Long id = user.getId();
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdatedBy(loginUser.getAccount());

        //如果未激活， 就给激活
        if(Status.INACTIVE.equals(user.getStatus())){
            user.setStatus(Status.ACTIVE);
        }
        //如果当前登录的是管理员，可以更新任何用户
        if (isAdmin(loginUser)){
            return userMapper.updateById(user);
        }
        //如果当前登录的不是管理员，只允许更新当前自己的信息
        if(!isAdmin(loginUser) && !id.equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User oldUser = userMapper.selectById(id);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return userMapper.updateById(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if(request == null){
            return null;
        }
        Object user = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (user == null){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) user;
    }

    /**
     * 是否为管理员 仅管理员
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        //仅管理员
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    /**
     * 是否为管理员
     * @param loginUser
     * @return
     */
    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public List<User> matchUsers(long num, User loginUser) {
        return null;
    }

    @Override
    public int updateUserById(User user, User loginUser,HttpServletRequest request) {
        //被修改的用户id
        Long id = user.getId();
        if (id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdatedBy(loginUser.getAccount());
        //逻辑删除
        user.setStatus(Status.DELETED);

        //只有管理员才能删除 且被删的不是自己
        if (isAdmin(loginUser) && id.equals(loginUser.getId())) {
            return userMapper.updateById(user);
        }else if (isAdmin(loginUser) && id.equals(loginUser.getId())){
            //自己删除自己，删除后退出登录
            int deletedId = userMapper.updateById(user);
            request.getSession().removeAttribute(USER_LOGIN_STATUS);
            return deletedId;
        }else {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
    }
}
