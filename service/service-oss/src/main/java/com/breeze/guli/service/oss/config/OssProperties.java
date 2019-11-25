package com.breeze.guli.service.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author breeze
 * @date 2019/11/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class OssProperties {

    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;



}
