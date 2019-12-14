package com.hyp.learn.file;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.file
 * hyp create at 19-12-14
 **/
public class FileUploadUtils {
    public static final String fileDir = "files/";

    public static final String mainDir = "/home/hyp/Downloads/" + fileDir;


    //处理“普通数据项”信息的方法

    /**
     * 处理 普通数据项
     *
     * @param item
     */
    public static void handleFormField(FileItem item) {
        // 获取 普通数据项中的 name值
        String fieldName = item.getFieldName();

        // 获取 普通数据项中的 value值
        String value = "";
        try {
            value = item.getString("utf-8");  // 以 utf-8的编码格式来解析 value值
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 输出到控制台
        System.out.println("fieldName:" + fieldName + "--value:" + value);
    }

    //处理“文件数据项”信息的方法

    /**
     * 处理 文件数据项
     *
     * @param item
     * @param fileNames
     */
    public static void handleFileField(FileItem item, List<String> fileNames) {
        // 获取 文件数据项中的 文件名
        String fileName = item.getName();
        String newFileName = null;


        // 判断 此文件的文件名是否合法
        if (fileName == null || "".equals(fileName)) {
            return;
        }

        // 控制只能上传图片
        if (!item.getContentType().startsWith("image")) {
            return;
        }

        // 将文件信息 输出到控制台
        System.out.println("fileName:" + fileName);         // 文件名
        System.out.println("fileSize:" + item.getSize());   // 文件大小


        int delimiter = fileName.lastIndexOf("/");
        if (delimiter == -1) {
            newFileName = UUID.randomUUID() + "_" + fileName.substring(delimiter + 1);
        } else {
            newFileName = UUID.randomUUID() + "_" + fileName;
        }


        // 获取 当前项目下的 /files 目录的绝对位置
//        FileUpload02Servlet内
//        this.getServletContext().getRealPath("/files");

        //指定路径
        String path = mainDir;


        File file = new File(path);   // 创建 file对象

        // 创建 /files 目录
        if (!file.exists()) {
            System.out.println("创建" + path + "文件夹:" + file.mkdir());
        }

//        try {
//            newFileName = URLEncoder.encode(newFileName, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        //加入list
        fileNames.add(newFileName);

        // 将文件保存到服务器上（UUID是通用唯一标识码，不用担心会有重复的名字出现）
        try {
            item.write(new File(file.toString(), newFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
