package com.wuyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.BlogFile;
import com.wuyun.entity.SiteConfig;
import com.wuyun.mapper.BlogFileMapper;
import com.wuyun.mapper.SiteConfigMapper;
import com.wuyun.service.RedisService;
import com.wuyun.service.SiteConfigService;
import com.wuyun.strategy.context.UploadStrategyContext;
import com.wuyun.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static com.wuyun.constant.CommonConstant.FALSE;
import static com.wuyun.constant.RedisConstant.SITE_SETTING;
import static com.wuyun.enums.FilePathEnum.*;

/**
 * 网站配置业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/14
 */
@Service
@RequiredArgsConstructor
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService {

    private final SiteConfigMapper siteConfigMapper;

    private final RedisService redisService;

    private final BlogFileMapper blogFileMapper;

    private final UploadStrategyContext uploadStrategyContext;


    @Override
    public SiteConfig getSiteConfig() {
        SiteConfig siteConfig = redisService.getObject(SITE_SETTING);
        //如果为空
        if (Objects.isNull(siteConfig)) {
            // 从数据库中加载
            siteConfig = siteConfigMapper.selectById(1);
            //存入redis缓存
            redisService.setObject(SITE_SETTING, siteConfig);
        }
        return siteConfig;
    }

    @Override
    public void updateSiteConfig(SiteConfig siteConfig) {
        //更新网站配置信息
        baseMapper.updateById(siteConfig);
        //删除redis中缓存的信息
        redisService.deleteObject(SITE_SETTING);
    }

    @Override
    public String uploadSiteImg(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, CONFIG.getPath());
        try {
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtension(file);
            BlogFile existFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                    .select(BlogFile::getId)
                    .eq(BlogFile::getFileName, md5)
                    .eq(BlogFile::getFilePath, CONFIG.getFilePath()));
            if (Objects.isNull(existFile)) {
                // 保存文件信息
                BlogFile newFile = BlogFile.builder()
                        .fileUrl(url)
                        .fileName(md5)
                        .filePath(CONFIG.getFilePath())
                        .extendName(extName)
                        .fileSize((int) file.getSize())
                        .isDir(FALSE)
                        .build();
                blogFileMapper.insert(newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }


}
