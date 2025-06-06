package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benefit.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author feng.dai
 * @Date 2023/3/2 12:29
 * @Project_Name mybatisPlus
 * @Package_Name com.df.mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //所有的CRUD均已完成
    //不用再跟以前一样配置一堆文件
}
