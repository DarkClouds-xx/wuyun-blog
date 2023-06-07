package com.wuyun.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuyun.entity.BlogFile;
import com.wuyun.exception.ServiceException;
import com.wuyun.mapper.BlogFileMapper;
import com.wuyun.model.dto.ConditionDTO;
import com.wuyun.model.dto.FolderDTO;
import com.wuyun.model.vo.FileVO;
import com.wuyun.model.vo.PageResult;
import com.wuyun.service.BlogFileService;
import com.wuyun.strategy.context.UploadStrategyContext;
import com.wuyun.utils.FileUtils;
import com.wuyun.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.wuyun.constant.CommonConstant.FALSE;
import static com.wuyun.constant.CommonConstant.TRUE;

/**
 * 文件业务接口实现类
 *
 * @author DarkClouds
 * @date 2023/05/13
 */
@Service
@RequiredArgsConstructor
public class BlogFileServiceImpl extends ServiceImpl<BlogFileMapper, BlogFile> implements BlogFileService {

    /**
     * 本地路径
     */
    @Value("${upload.local.path}")
    private String localPath;

    private final BlogFileMapper blogFileMapper;

    private final UploadStrategyContext uploadStrategyContext;

    private final HttpServletResponse response;

    @Override
    public PageResult<FileVO> listFileVOList(ConditionDTO condition) {
        // 查询文件数量
        Long count = blogFileMapper.selectCount(new LambdaQueryWrapper<BlogFile>()
                .eq(BlogFile::getFilePath, condition.getFilePath()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询文件列表
        List<FileVO> fileVOList = blogFileMapper.selectFileVOList(PageUtils.getLimit(), PageUtils.getSize(),
                condition.getFilePath());
        return new PageResult<>(fileVOList, count);
    }

    @Override
    public void uploadFile(MultipartFile file, String path) {
        try {
            //判断路径是否是 / 如果不是则在路径后面加上 /
            String uploadPath = "/".equals(path) ? path : path + "/";
            // 上传文件
            String url = uploadStrategyContext.executeUploadStrategy(file, uploadPath);
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtension(file);
            BlogFile existFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                    .select(BlogFile::getId)
                    .eq(BlogFile::getFileName, md5)
                    .eq(BlogFile::getFilePath, path));
            Assert.isNull(existFile, "文件已存在");
            // 保存文件信息
            BlogFile newFile = BlogFile.builder()
                    .fileUrl(url)
                    .fileName(md5)
                    .filePath(path)
                    .extendName(extName)
                    .fileSize((int) file.getSize())
                    .isDir(FALSE)
                    .build();
            blogFileMapper.insert(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createFolder(FolderDTO folder) {
        String fileName = folder.getFileName();
        String filePath = folder.getFilePath();
        // 判断目录是否存在
        BlogFile blogFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                .select(BlogFile::getId)
                .eq(BlogFile::getFilePath, folder.getFilePath())
                .eq(BlogFile::getFileName, fileName));
        Assert.isNull(blogFile, "目录已存在");
        // 创建目录
        File directory = new File(localPath + filePath + "/" + fileName);
        if (FileUtils.mkdir(directory)) {
            BlogFile newBlogFile = BlogFile.builder()
                    .fileName(fileName)
                    .filePath(filePath)
                    .isDir(TRUE)
                    .build();
            blogFileMapper.insert(newBlogFile);
        } else {
            throw new ServiceException("创建目录失败");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(List<Integer> fileIdList) {
        List<BlogFile> blogFiles = blogFileMapper.selectList(new LambdaQueryWrapper<BlogFile>()
                .select(BlogFile::getFileName, BlogFile::getFilePath, BlogFile::getExtendName, BlogFile::getIsDir)
                .in(BlogFile::getId, fileIdList));
        // 删除数据库中的文件信息
        blogFileMapper.deleteBatchIds(fileIdList);
        blogFiles.forEach(blogFile -> {
            File file;
            String fileName = localPath + blogFile.getFilePath() + "/" + blogFile.getFileName();
            if (blogFile.getIsDir().equals(TRUE)) {
                // 删除数据库中剩余的子文件
                String filePath = blogFile.getFilePath() + blogFile.getFileName();
                blogFileMapper.delete(new LambdaQueryWrapper<BlogFile>().eq(BlogFile::getFilePath, filePath));
                // 删除目录
                file = new File(fileName);
                if (file.exists()) {
                    FileUtils.deleteFile(file);
                }
            } else {
                // 删除文件
                file = new File(fileName + "." + blogFile.getExtendName());
                if (file.exists()) {
                    file.delete();
                }
            }
        });
    }

    @Override
    public void downloadFile(Integer fileId) {
        // 查询文件信息
        BlogFile blogFile = blogFileMapper.selectOne(new LambdaQueryWrapper<BlogFile>()
                .select(BlogFile::getFilePath, BlogFile::getFileName,
                        BlogFile::getExtendName, BlogFile::getIsDir)
                .eq(BlogFile::getId, fileId));
        Assert.notNull(blogFile, "文件不存在");
        String filePath = localPath + blogFile.getFilePath() + "/";
        // 要下载的不是目录
        if (blogFile.getIsDir().equals(FALSE)) {
            String fileName = blogFile.getFileName() + "." + blogFile.getExtendName();
            downloadFile(filePath, fileName);
        } else {
            // 下载的是目录则压缩下载
            String fileName = filePath + blogFile.getFileName();
            File src = new File(fileName);
            File dest = new File(fileName + ".zip");
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(dest));
                // 压缩文件
                toZip(src, zipOutputStream, src.getName());
                zipOutputStream.close();
                // 下载压缩包
                downloadFile(filePath, blogFile.getFileName() + ".zip");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dest.exists()) {
                    dest.delete();
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    private void downloadFile(String filePath, String fileName) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            fileInputStream = new FileInputStream(filePath + fileName);
            outputStream = response.getOutputStream();
            IOUtils.copyLarge(fileInputStream, outputStream);
        } catch (IOException e) {
            throw new ServiceException("文件下载失败");
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 压缩文件夹
     *
     * @param src             源文件
     * @param zipOutputStream 压缩输出流
     * @param name            文件名
     * @throws IOException IO异常
     */
    private static void toZip(File src, ZipOutputStream zipOutputStream, String name) throws IOException {
        for (File file : Objects.requireNonNull(src.listFiles())) {
            if (file.isFile()) {
                // 判断文件，变成ZipEntry对象，放入到压缩包中
                ZipEntry zipEntry = new ZipEntry(name + "/" + file.getName());
                // 读取文件中的数据，写到压缩包
                zipOutputStream.putNextEntry(zipEntry);
                FileInputStream fileInputStream = new FileInputStream(file);
                int b;
                while ((b = fileInputStream.read()) != -1) {
                    zipOutputStream.write(b);
                }
                fileInputStream.close();
                zipOutputStream.closeEntry();
            } else {
                toZip(file, zipOutputStream, name + "/" + file.getName());
            }
        }
    }
}
