package edu.cslg.easyshopping.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class UploadUtil {
    public static String upImg(MultipartFile file){
        String root = "uploadDir/";
        String fileName = UUID.randomUUID()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filePath = file.getClass().getResource("/").getPath()+"static/"+root;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(filePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root + fileName;
    }

}
