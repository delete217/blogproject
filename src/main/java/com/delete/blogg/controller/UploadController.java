package com.delete.blogg.controller;

import com.delete.blogg.utils.QiniuUtils;
import com.delete.blogg.vo.Result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //随机的字符串 得到唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传文件
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            return Result.success(qiniuUtils.url + fileName);
        }
        return Result.fail(20001,"上传失败");

    }
}
