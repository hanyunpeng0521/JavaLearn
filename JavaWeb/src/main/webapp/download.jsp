<%--
  Created by IntelliJ IDEA.
  User: hyp
  Date: 19-12-14
  Time: 下午6:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>

<br>
--------------直接下载------------
<br>
<%
    List<String> fileNames = (List<String>) request.getAttribute("fileNames");
    for (String fileName : fileNames) {
%>

<form action="upload" method="get">
    <input type="hidden" name="fileName" value="<%=fileName %>"/>
    <input type="submit" value="下载:<%=fileName %>"/>
</form>
<%
    }
%>
<br>
--------------直接打开---------
<%
    for (String fileName : fileNames) {
%>
<form action="upload" method="get">
    <input type="hidden" name="fileName" value="<%=fileName %>"/>
    <input type="hidden" name="online" value="true"/>
    <input type="submit" value="打开:<%=fileName %>"/>
</form>
<%
    }
%>
</br>

</body>
</html>
