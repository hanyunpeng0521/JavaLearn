package com.hyp.learn.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hyp
 * Project name is javaLearn
 * Include in com.hyp.learn.servlet
 * hyp create at 19-12-13
 **/
@MultipartConfig(location = "/home/hyp/Downloads")
@WebServlet(name = "upload", urlPatterns = {"/upload"})
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Part part = req.getPart("file");
        System.out.println(part.getHeaderNames());
        String fileName = part.getHeader("content-disposition");
        System.out.println(fileName);
        System.out.println(fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length() - 1));
        part.write(fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length() - 1));
        out.write("{'type':'1','msg':'success'}");
    }
}
