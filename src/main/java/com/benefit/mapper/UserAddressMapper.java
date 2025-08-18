package com.benefit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benefit.model.entity.UserAddress;
import com.benefit.request.UserAddressRequest;
import com.benefit.vo.UserAddressVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Allen
 * @Date 2025/8/18 10:06
 * @Description
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    List<UserAddressVo> selectPackageList(Page<UserAddressVo> page, UserAddressRequest request);
}
