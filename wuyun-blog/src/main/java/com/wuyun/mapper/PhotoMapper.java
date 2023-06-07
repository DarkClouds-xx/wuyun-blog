package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.Photo;
import com.wuyun.model.vo.PhotoBackVO;
import com.wuyun.model.vo.PhotoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 照片映射器
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
@Repository
public interface PhotoMapper extends BaseMapper<Photo> {

    /**
     * 查询后台照片列表
     *
     * @param limit   页码
     * @param size    大小
     * @param albumId 相册id
     * @return 后台照片列表
     */
    List<PhotoBackVO> selectPhotoBackVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("albumId") Integer albumId);

    /**
     * 查询照片列表
     *
     * @param albumId 相册id
     * @return 后台照片列表
     */
    List<PhotoVO> selectPhotoVOList(@Param("albumId") Integer albumId);
}
