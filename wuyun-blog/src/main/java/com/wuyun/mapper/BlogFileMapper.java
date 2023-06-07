package com.wuyun.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuyun.entity.BlogFile;
import com.wuyun.model.vo.FileVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 博客文件映射器
 *
 * @author DarkClouds
 * @date 2023/05/21
 */
@Repository
public interface BlogFileMapper extends BaseMapper<BlogFile> {

    /**
     * 查询后台文件列表
     *
     * @param limit    页码
     * @param size     大小
     * @param filePath 文件路径
     * @return 后台文件列表
     */
    List<FileVO> selectFileVOList(@Param("limit") Long limit,@Param("size") Long size,@Param("filePath") String filePath);
}
