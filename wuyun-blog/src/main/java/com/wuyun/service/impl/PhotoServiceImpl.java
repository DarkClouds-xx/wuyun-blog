package com.wuyun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.Album;
import com.wuyun.entity.BlogFile;
import com.wuyun.entity.Photo;
import com.wuyun.mapper.AlbumMapper;
import com.wuyun.mapper.BlogFileMapper;
import com.wuyun.mapper.PhotoMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.PhotoDTO;
import com.wuyun.model.dto.PhotoInfoDTO;
import com.wuyun.model.vo.AlbumBackVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.model.vo.PhotoBackVO;
import com.wuyun.model.vo.PhotoVO;
import com.wuyun.service.PhotoService;
import com.wuyun.strategy.context.UploadStrategyContext;
import com.wuyun.utils.BeanCopyUtils;
import com.wuyun.utils.FileUtils;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wuyun.constant.CommonConstant.FALSE;
import static com.wuyun.enums.FilePathEnum.PHOTO;

/**
 * 照片业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/17
 */
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    private final PhotoMapper photoMapper;

    private final AlbumMapper albumMapper;

    private final UploadStrategyContext uploadStrategyContext;

    private final BlogFileMapper blogFileMapper;

    @Override
    public PageResult<PhotoBackVO> listPhotoBackVO(ConditionDTO condition) {
        // 查询照片数量
        Long count = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Objects.nonNull(condition.getAlbumId()), Photo::getAlbumId, condition.getAlbumId()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询照片列表
        List<PhotoBackVO> photoList = photoMapper.selectPhotoBackVOList(PageUtils.getLimit(),
                PageUtils.getSize(), condition.getAlbumId());
        return new PageResult<>(photoList, count);
    }

    @Override
    public AlbumBackVO getAlbumInfo(Integer albumId) {
        AlbumBackVO albumBackVO = albumMapper.selectAlbumInfoById(albumId);
        if (Objects.isNull(albumBackVO)) {
            return null;
        }
        Long photoCount = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        albumBackVO.setPhotoCount(photoCount);
        return albumBackVO;
    }

    @Override
    public void addPhoto(PhotoDTO photo) {
        // 批量保存照片
        List<Photo> pictureList = photo.getPhotoUrlList().stream()
                .map(url -> Photo.builder()
                        .albumId(photo.getAlbumId())
                        .photoName(IdWorker.getIdStr())
                        .photoUrl(url)
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(pictureList);
    }

    @Override
    public void updatePhoto(PhotoInfoDTO photoInfo) {
        Photo photo = BeanCopyUtils.copyBean(photoInfo, Photo.class);
        baseMapper.updateById(photo);
    }

    @Override
    public void deletePhoto(List<Integer> photoIdList) {
        baseMapper.deleteBatchIds(photoIdList);
    }

    @Override
    public void movePhoto(PhotoDTO photo) {
        List<Photo> photoList = photo.getPhotoIdList().stream()
                .map(photoId -> Photo.builder()
                        .id(photoId)
                        .albumId(photo.getAlbumId())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(photoList);
    }

    @Override
    public Map<String, Object> listPhotoVO(ConditionDTO condition) {
        Map<String, Object> result = new HashMap<>(2);
        String albumName = albumMapper.selectOne(new LambdaQueryWrapper<Album>()
                        .select(Album::getAlbumName).eq(Album::getId, condition.getAlbumId()))
                .getAlbumName();
        List<PhotoVO> photoVOList = photoMapper.selectPhotoVOList(condition.getAlbumId());
        result.put("albumName", albumName);
        result.put("photoVOList", photoVOList);
        return result;
    }

    @Override
    public String uploadPhoto(MultipartFile file) {
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
}
