package com.baizhi.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
public class AliYun {

    public static String upload(MultipartFile file,String path) throws IOException {
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tKWAEydo2hsffCrqcdT";
        String accessKeySecret = "XREgaH3vFpTrzuEDQojwLlCe1uijeq";
        String bucketName = "2101wad";  //存储空间名
        String originalFilename = file.getOriginalFilename();
        String fileName = path+UUID.randomUUID().toString().replace("-", "") + originalFilename.substring(originalFilename.lastIndexOf('.'));

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。 参数：Bucket名字，指定文件名，文件本地路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream());
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return fileName;
    }

    public static void deleteFile(String fileName,String path){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tKWAEydo2hsffCrqcdT";
        String accessKeySecret = "XREgaH3vFpTrzuEDQojwLlCe1uijeq";
        String bucketName = "2101wad";  //存储空间名
        String objectName =path+fileName ;  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static URL cutVideo(String filePath){
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tKWAEydo2hsffCrqcdT";
        String accessKeySecret = "XREgaH3vFpTrzuEDQojwLlCe1uijeq";
        String bucketName = "2101wad";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_50000,f_jpg,w_500,h_800";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, filePath, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        // 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl;
    }

    public static String uploadStream(URL url,String filePath) throws IOException {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tKWAEydo2hsffCrqcdT";
        String accessKeySecret = "XREgaH3vFpTrzuEDQojwLlCe1uijeq";
        String bucketName = "2101wad";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String[] split = filePath.split("\\.");
        String coverPath=split[0]+".jpg";
        // 上传网络流。
        InputStream inputStream = new URL(url.toString()).openStream();
        ossClient.putObject(bucketName, coverPath, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        return coverPath;
    }

    public static String download(String url){
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tKWAEydo2hsffCrqcdT";
        String accessKeySecret = "XREgaH3vFpTrzuEDQojwLlCe1uijeq";
        String bucketName = "2101wad";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String[] split = url.split("/");
        //https://2101wad.oss-cn-beijing.aliyuncs.com/736f26fe90f34094812b5036dee55c45.jpg

        String fileName = split[split.length - 1];
        String localFile = "E:\\wad\\pictures\\" + fileName;
        ossClient.getObject(new GetObjectRequest(bucketName, fileName), new File(localFile));
        ossClient.shutdown();
        return localFile;
    }
}
