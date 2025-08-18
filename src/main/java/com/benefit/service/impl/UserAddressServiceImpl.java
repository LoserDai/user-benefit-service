package com.benefit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benefit.common.PageResult;
import com.benefit.mapper.UserAddressMapper;
import com.benefit.model.entity.UserAddress;
import com.benefit.request.UserAddressRequest;
import com.benefit.service.UserAddressService;
import com.benefit.vo.UserAddressVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author Allen
 * @Date 2025/8/18 10:04
 * @Description
 */

@Service
@Slf4j
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {


    @Resource
    private UserAddressMapper userAddressMapper;


    /**
    * @Description: add user address
    * @Param: [request]
    * @Return: java.lang.Integer
    * @Author: Allen
    */
    @Override
    public Integer addAddress(UserAddressRequest request) {

        UserAddress userAddress = new UserAddress();

        userAddress.setUserId(request.getUserId());
        userAddress.setDetailAddress(request.getDetailAddress());
        userAddress.setCity(request.getCity());
        userAddress.setStatus(Optional.of(request.getStatus()).orElse(1));
        userAddress.setDistrict(request.getDistrict());
        userAddress.setProvince(request.getProvince());
        userAddress.setPostalCode(request.getPostalCode());
        userAddress.setReceiverName(request.getReceiverName());
        userAddress.setReceiverPhone(request.getReceiverPhone());
        userAddress.setCreateTime(LocalDateTime.now());
        userAddress.setUpdateTime(LocalDateTime.now());

        int count = userAddressMapper.insert(userAddress);
        return count;
    }

    /**
    * @Description: remove userAddress
    * @Param: [request]
    * @Return: java.lang.Integer
    * @Author: Allen
    */
    @Override
    public Integer removeAddress(UserAddressRequest request) {
        UserAddress userAddress = new UserAddress();

        userAddress.setId(request.getId());
        userAddress.setUserId(request.getUserId());
        userAddress.setStatus(0);
        userAddress.setUpdateTime(LocalDateTime.now());

        int count = userAddressMapper.updateById(userAddress);
        return count;
    }

    /**
    * @Description: update user address
    * @Param: [request]
    * @Return: java.lang.Integer
    * @Author: Allen
    */
    @Override
    public Integer updateAddress(UserAddressRequest request) {

        UserAddress userAddress = new UserAddress();
        userAddress.setId(request.getId());

        userAddress.setDetailAddress(request.getDetailAddress());
        userAddress.setCity(request.getCity());
        userAddress.setDistrict(request.getDistrict());
        userAddress.setProvince(request.getProvince());
        userAddress.setPostalCode(request.getPostalCode());
        userAddress.setReceiverName(request.getReceiverName());
        userAddress.setReceiverPhone(request.getReceiverPhone());

        if (request.getStatus() != null) {
            userAddress.setStatus(request.getStatus());
        }

        return userAddressMapper.updateById(userAddress);
    }

    @Override
    public PageResult<UserAddressVo> queryAllUserAddress(UserAddressRequest request) {
        int pageNum = Optional.ofNullable(request)
                .map(UserAddressRequest::getPageNum)
                .filter(num -> num > 0)
                .orElse(1);

        int pageSize = Optional.ofNullable(request)
                .map(UserAddressRequest::getPageSize)
                .filter(size -> size > 0 && size <= 500)
                .orElse(10);

        Page<UserAddressVo> page = new Page<>(pageNum, pageSize);

        List<UserAddressVo> list = userAddressMapper.selectPackageList(page, request);
        return new PageResult<>(
                list,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }
}
