package com.wuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuyun.entity.Album;
import com.wuyun.model.dto.AlbumDTO;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.vo.AlbumBackVO;
import com.wuyun.model.vo.AlbumVO;
import com.wuyun.model.vo.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 相册业务接口
 *
 * @author DarkClouds
 * @date 2023/05/16
 */
public interface AlbumService extends IService<Album> {

    /**
     * 查看后台相册列表
     *
     * @param condition 条件
     * @return 后台相册列表
     */
    PageResult<AlbumBackVO> listAlbumBackVO(ConditionDTO condition);

    /**
     * 上传相册封面
     *
     * @param file 文件
     * @return 相册封面地址
     */
    String uploadAlbumCover(MultipartFile file);

    /**
     * 添加相册
     *
     * @param album 相册
     */
    void addAlbum(AlbumDTO album);

    /**
     * 删除相册
     *
     * @param albumId 相册id
     */
    void deleteAlbum(Integer albumId);

    /**
     * 修改相册
     *
     * @param album 相册
     */
    void updateAlbum(AlbumDTO album);

    /**
     * 编辑相册
     *
     * @param albumId 相册id
     * @return 相册信息
     */
    AlbumDTO editAlbum(Integer albumId);

    /**
     * 查看相册列表
     *
     * @return 相册列表
     */
    List<AlbumVO> listAlbumVO();
}
