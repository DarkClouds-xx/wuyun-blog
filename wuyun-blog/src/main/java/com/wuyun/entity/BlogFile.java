package com.wuyun.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 博客文件
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("b_blog_file")
public class BlogFile {
    /**
     * 文件id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件url
     */
    private String fileUrl;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 文件类型
     */
    private String extendName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 是否为目录 (0否 1是)
     */
    private Integer isDir;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}