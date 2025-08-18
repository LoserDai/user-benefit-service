package com.benefit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.benefit.common.PageResult;
import com.benefit.model.entity.UserAddress;
import com.benefit.request.UserAddressRequest;
import com.benefit.vo.UserAddressVo;

/**
 * @Author Allen
 * @Date 2025/8/18 9:46
 * @Description
 */
public interface UserAddressService extends IService<UserAddress> {
    Integer addAddress(UserAddressRequest request);

    Integer removeAddress(UserAddressRequest request);

    Integer updateAddress(UserAddressRequest request);

    PageResult<UserAddressVo> queryAllUserAddress(UserAddressRequest request);
}
