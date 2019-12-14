<html>
<head>
    <%--<%--%>
    <%--String path = request.getContextPath();--%>
    <%--String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";--%>
    <%--%>--%>
    <%--<base href="<%=basePath%>">--%>
    <title>hello</title>
</head>
<body>
<h2>Hello World!</h2>
<form method="POST" enctype="multipart/form-data" action="upload">
    File to upload: <input type="file" name="upfile"><br/>
    Notes about the file: <input type="text" name="note"><br/>
    <br/>
    <input type="submit" value="Press"> to upload the file!
</form>
</body>
</html>
