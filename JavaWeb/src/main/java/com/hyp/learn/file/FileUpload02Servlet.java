package com.hyp.learn.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.file
 * hyp create at 19-12-14
 **/
@MultipartConfig(location = "/home/hyp/Downloads")
@WebServlet(name = "upload", urlPatterns = {"/upload"})
public class FileUpload02Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String aFileName = request.getParameter("fileName");
        String online = request.getParameter("online");
        FileInputStream in = null;
        ServletOutputStream out = null;
        boolean isOnLine = online != null;
        try {


            if (isOnLine) {
                URL u = new URL("file:///" + FileUploadUtils.mainDir + aFileName);
                response.setContentType(u.openConnection().getContentType());
                response.setHeader("Content-Disposition", "inline; filename=" + aFileName);
            } else {
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + aFileName);
            }

            //f8cbfaa1-202f-4c23-8ee5-305ae8884952_2019-12-13%2012-40-42%20的屏幕截图.png
            //f8cbfaa1-202f-4c23-8ee5-305ae8884952_2019-12-13+12-40-42+的屏幕截图.png
            in = new FileInputStream(FileUploadUtils.mainDir + aFileName);
            out = response.getOutputStream();
            out.flush();
            int aRead = 0;
            while ((aRead = in.read()) != -1) {
                out.write(aRead);
            }
            out.flush();

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(in).close();
                Objects.requireNonNull(out).close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // 判断是普通表单，还是文件上传表单
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("不是文件上传表单！");
        }


        // 创建上传所需要的两个对象
        // 磁盘文件对象
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        // 文件上传对象
        ServletFileUpload fu = new ServletFileUpload(fileFactory);
        // 设置解析文件上传中的文件名的编码格式
        fu.setHeaderEncoding("utf-8");

        // 限制单个文件的大小
        fu.setFileSizeMax(1024 * 1024);
        // 限制上传的总文件大小
        fu.setSizeMax(1024 * 1024 * 10);

        // 创建 list容器用来保存 表单中的所有数据信息
        List<FileItem> items = new ArrayList<FileItem>();


        // 将表单中的所有数据信息放入 list容器中
        try {
            items = fu.parseRequest(request);
        } catch (FileUploadException e) {
            System.out.println("单个文件大小不能超过1MB，总文件大小不能超过10MB");
            e.printStackTrace();

        }

        System.out.println(items);

        List<String> fileNames = new ArrayList<String>();


        // 遍历 list容器，处理 每个数据项 中的信息
        for (FileItem item : items) {
            // 判断是否是普通项
            if (item.isFormField()) {
                // 处理 普通数据项 信息
                FileUploadUtils.handleFormField(item);
            } else {
                // 处理 文件数据项 信息
                FileUploadUtils.handleFileField(item, fileNames);
            }
        }


        request.setAttribute("fileNames", fileNames);
        request.getRequestDispatcher("download.jsp").forward(request, response);

    }


}
