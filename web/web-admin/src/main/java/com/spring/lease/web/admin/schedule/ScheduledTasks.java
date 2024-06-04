package com.spring.lease.web.admin.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.spring.lease.model.entity.LeaseAgreement;
import com.spring.lease.model.enums.LeaseStatus;
import com.spring.lease.web.admin.service.LeaseAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 4/6/2024 10:57 周二
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Component
public class ScheduledTasks {
    @Autowired
    private LeaseAgreementService leaseAgreementService;
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkLeaseStatus(){
        Date date = new Date(); // 创建当前日期
        // 封装对象
        LambdaUpdateWrapper<LeaseAgreement> leaseAgreementLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        leaseAgreementLambdaUpdateWrapper
                .le(LeaseAgreement::getLeaseEndDate,date)
                .in(LeaseAgreement::getStatus, LeaseStatus.SIGNED,LeaseStatus.WITHDRAWING)
                .set(LeaseAgreement::getStatus,LeaseStatus.EXPIRED);
        // 更新对象
        leaseAgreementService.update(leaseAgreementLambdaUpdateWrapper);
    }
}
