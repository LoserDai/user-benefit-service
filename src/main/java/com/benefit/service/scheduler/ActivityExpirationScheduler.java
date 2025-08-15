package com.benefit.service.scheduler;

import com.benefit.mapper.BenefitActivityMapper;
import com.benefit.model.entity.BenefitActivity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Allen
 * @date 2025/8/13 16:17
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityExpirationScheduler {

    private final BenefitActivityMapper benefitActivityMapper;

    /**
     * 定时关闭已过期的活动
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    public void checkAndEndExpiredActivities() {
        // 查询所有需要结束的活动
        List<BenefitActivity> activities = benefitActivityMapper.selectExpiredActivities();

        if (!activities.isEmpty()) {
            log.info("Found {} activities to end", activities.size());

            // 批量更新状态为 ENDED
            int count = benefitActivityMapper.batchUpdateToEnded(activities);

            log.info("Successfully ended {} activities", count);
        }
    }

    /**
     * 定时开启已开始的活动
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional
    public void checkAndStartExpiredActivities() {
        // 查询所有需要开始的活动
        List<BenefitActivity> activities = benefitActivityMapper.selectNotStartActivities();

        if (!activities.isEmpty()) {
            log.info("Found {} activities to start", activities.size());

            // 批量更新状态为 ONGOING
            int count = benefitActivityMapper.batchUpdateToStart(activities);

            log.info("Successfully started {} activities", count);
        }
    }
}
