package com.ocean.platformspringboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ocean.platformspringboot.entity.PictureFile;
import com.ocean.platformspringboot.mapper.PictureFileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/file")
public class PictureFileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private PictureFileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type ;
        File uploadFile = new File(fileUploadPath + fileUUID);
        File uploadParentFile = uploadFile.getParentFile();
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        PictureFile dbFiles = getFileByMd5(md5);
        System.out.println(uploadFile);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://127.0.0.1:8081/file/" +fileUUID;
        }
        //存储数据库
        PictureFile saveFile = new PictureFile();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUUID) ;
        ServletOutputStream os  = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

    }
    private PictureFile getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<PictureFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<PictureFile> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }


}
