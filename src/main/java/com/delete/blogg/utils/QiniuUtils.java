package com.delete.blogg.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

@Component
public class QiniuUtils {
    public static final String url = "http://rkrgm73lr.hn-bkt.clouddn.com/";

    //...生成上传凭证，然后准备上传
    String accessKey = "mOE0MgFFEPfAZlmfOCrvJeOpV7wqbcHe4zo-RyE7";
    String secretKey = "1cN-CaZLJyS001KypYiTbLM6QukhmbpTrST1slDy";
    String bucket = "delete01";

    public boolean upload(MultipartFile file,String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        try{
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
}
