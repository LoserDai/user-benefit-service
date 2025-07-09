package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Allen
 * @date 2025/6/6 15:16
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param account   用户账户
     * @param password  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    User userRegister(String account, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param account  用户账户
     * @param password 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String account, String password, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user, User loginUser);

    /**
     * 获取当前登录用户信息
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);

    /**
     * 匹配用户
     * @param num
     * @param loginUser
     * @return
     */
    List<User> matchUsers(long num, User loginUser);

    int updateUserById(User user,User loginUser,HttpServletRequest request);
}
