package com.wuyun.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.Album;
import com.wuyun.entity.BlogFile;
import com.wuyun.entity.Photo;
import com.wuyun.mapper.AlbumMapper;
import com.wuyun.mapper.BlogFileMapper;
import com.wuyun.mapper.PhotoMapper;
import com.wuyun.model.dto.AlbumDTO;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.AlbumBackVO;
import com.wuyun.model.vo.AlbumVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.service.AlbumService;
import com.wuyun.strategy.context.UploadStrategyContext;
import com.wuyun.utils.BeanCopyUtils;
import com.wuyun.utils.FileUtils;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.wuyun.constant.CommonConstant.FALSE;
import static com.wuyun.enums.FilePathEnum.PHOTO;

/**
 * 相册业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    private final AlbumMapper albumMapper;

    private final UploadStrategyContext uploadStrategyContext;

    private final BlogFileMapper blogFileMapper;

    private final PhotoMapper photoMapper;

    @Override
    public PageResult<AlbumBackVO> listAlbumBackVO(ConditionDTO condition) {
        // 查询相册数量
        Long count = albumMapper.selectCount(new LambdaQueryWrapper<Album>()
                .like(StringUtils.hasText(condition.getKeyword()), Album::getAlbumName, condition.getKeyword()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询相册信息
        List<AlbumBackVO> albumList = albumMapper.selectAlbumBackVO(PageUtils.getLimit(), PageUtils.getSize(), condition.getKeyword());
        return new PageResult<>(albumList, count);
    }

    @Override
    public String uploadAlbumCover(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, PHOTO.getPath());
        try {
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtension(file);
            BlogFile existFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                    .select(BlogFile::getId)
                    .eq(BlogFile::getFileName, md5)
                    .eq(BlogFile::getFilePath, PHOTO.getFilePath()));
            if (Objects.isNull(existFile)) {
                // 保存文件信息
                BlogFile newFile = BlogFile.builder()
                        .fileUrl(url)
                        .fileName(md5)
                        .filePath(PHOTO.getFilePath())
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

    @Override
    public void addAlbum(AlbumDTO album) {
        // 相册是否存在
        Album existAlbum = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
                .select(Album::getId)
                .eq(Album::getAlbumName, album.getAlbumName()));
        Assert.isNull(existAlbum, album.getAlbumName() + "相册已存在");
        // 添加新相册
        Album newAlbum = BeanCopyUtils.copyBean(album, Album.class);
        baseMapper.insert(newAlbum);
    }

    @Override
    public void deleteAlbum(Integer albumId) {
        // 查询照片数量
        Long count = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        Assert.isFalse(count > 0, "相册下存在照片");
        // 不存在照片则删除
        albumMapper.deleteById(albumId);
    }

    @Override
    public void updateAlbum(AlbumDTO album) {
        // 相册是否存在
        Album existAlbum = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
                .select(Album::getId)
                .eq(Album::getAlbumName, album.getAlbumName()));
        Assert.isFalse(Objects.nonNull(existAlbum) && !existAlbum.getId().equals(album.getId()),
                album.getAlbumName() + "相册已存在");
        // 修改相册
        Album newAlbum = BeanCopyUtils.copyBean(album, Album.class);
        baseMapper.updateById(newAlbum);
    }

    @Override
    public AlbumDTO editAlbum(Integer albumId) {
        return albumMapper.selectAlbumById(albumId);
    }

    @Override
    public List<AlbumVO> listAlbumVO() {
        return albumMapper.selectAlbumVOList();
    }
}
