package com.spring.lease.common.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 1/6/2024 11:13 周六
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties {
    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
