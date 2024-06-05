package com.spring.lease.common.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 5/6/2024 19:52 周三
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */


@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSMSProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;
}
