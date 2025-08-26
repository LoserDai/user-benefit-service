package com.benefit.service.scheduler;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.benefit.mapper.OrderMainMapper;
import com.benefit.model.entity.OrderMain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Allen
 * @Date 2025/8/26 11:24
 * @Description
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMainExpirationScheduler {

    private final OrderMainMapper orderMainMapper;


    /**
    * @Description: close timeout orderMains
    * @Param: []
    * @Return: void
    * @Author: Allen
    */
    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    public void checkAndCloseExpireOrder(){

        //查询需要关闭的orderMain
        List<OrderMain> list = orderMainMapper.checkAndCloseExpireOrder();
        //关闭
        if (!list.isEmpty()){
            log.info("Found {} orderMain to end", list.size());
            Integer count = orderMainMapper.batchCancelOrder(list);

            log.info("Successfully ended {} orderMains", count);
        }
    }

}
