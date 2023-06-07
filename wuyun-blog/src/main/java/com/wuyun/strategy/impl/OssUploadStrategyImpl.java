package com.wuyun.strategy.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.wuyun.config.properties.OssProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;


/**
 * oss上传策略
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Slf4j
@RequiredArgsConstructor
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends AbstractUploadStrategyImpl {

    //构造器注入bean
    private final OssProperties ossProperties;

    @Override
    public Boolean exists(String filePath) {
        return getOssClient().doesObjectExist(ossProperties.getBucketName(), filePath);
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        OSS ossClient = getOssClient();
        try {
            //OSS上传不能以"/"开头，如果路径只有"/"则把路径设置为空
            //String uploadPath = "/".equals(path) ? "" : path.split("/")[1] + "/";
            // 调用oss方法上传
            ossClient.putObject(ossProperties.getBucketName(), path + fileName, inputStream);
        } catch (OSSException oe) {
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.info("Request ID:" + oe.getRequestId());
            log.info("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return ossProperties.getUrl() + filePath;
    }

    /**
     * 获取ossClient
     *
     * @return {@link OSS} ossClient
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }
}
