package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.User;
import com.benefit.vo.UserDashVo;
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
}
