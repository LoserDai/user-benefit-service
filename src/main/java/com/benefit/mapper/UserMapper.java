package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.User;
import com.benefit.request.UserRequest;
import com.benefit.vo.UserDashVo;
import com.benefit.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author feng.dai
 * @Date 2023/3/2 12:29
 * @Project_Name mybatisPlus
 * @Package_Name com.df.mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<UserDashVo> getUserRegisterCount();

    Integer getUserCount();

    List<UserVo> pageQueryUser(Page<User> page, UserRequest request);
}
