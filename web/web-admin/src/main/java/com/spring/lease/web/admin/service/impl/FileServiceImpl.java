package com.spring.lease.web.admin.service.impl;

import com.spring.lease.common.minio.MinioProperties;
import com.spring.lease.web.admin.service.FileService;
import io.minio.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient client;
    @Override
    public String upload(MultipartFile file) {
        try {
            // 判断服务器中某个桶是否存在
            boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!bucketExists) {
                // 如果服务器中某个桶不存在就创建他
                client.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }

//            String filename = new SimpleDateFormat("yyyyMMdd")
//                    .format(new Date()) + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
//            client.putObject(PutObjectArgs.builder().
//                    bucket(minioProperties.getBucketName()).
//                    object(filename).
//                    stream(file.getInputStream(), file.getSize(), -1).
//                    contentType(file.getContentType()).build());
//            return String.join("/", minioProperties.getEndpoint(), minioProperties.getBucketName(), filename);


            // 获取上传文件名 + 使用UUID随机生成一个字符串作为文件的前缀  + 格式化当期日期
            String newFileName = DateFormatUtils.format(new Date(),"yyyy/MM/dd")+UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename();
            //获取输入流
            InputStream is = file.getInputStream();
            //创建PutObjectArgs对象
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    //设置桶的名字
                    .bucket(minioProperties.getBucketName())
                    //指定上传到Minio服务器上之后的名字
                    .object(newFileName)
                    .stream(is,is.available(),-1)
                    .build();
            //通过客户端向Minio服务器上传对象
            client.putObject(putObjectArgs);
            //返回文件在minio的地址，http://192.168.6.100:9000/lease/文件名
            return minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+"/"+newFileName;
        } catch (Exception e) {
            System.out.println("Error occurred:" + e);
        }
        return null;
    }

}

