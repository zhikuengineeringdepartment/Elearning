<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台-课程创建</title>
</head>
<br/>
<body>
<h2>创建课程</h2>
    <form action="/backstage/course/create" method="post"  enctype="multipart/form-data">
        <label>
            课程名称：
            <input type="text" name="title"/>
        </label><br>
        <label>
            课程描述：<br>
            <textarea name="describe" style="width:200px;height:80px;" value=""></textarea>
        </label><br>
        <label>
            课程图标地址：
            <input type="text" name="icon_path" value=""/>
        </label><br>
        课程文件（.md/.txt,可稍后上传）:<br>
        <input type="file" name="file" ><br>

        <input type="submit" value="提交"/>
    </form>
<%
    String lastCourseId = (String)request.getAttribute("lastCourseId");
    if(lastCourseId != null) {
%>
课程创建成功,id:<%= lastCourseId%>
<% } %>
<h2>上传课程内容</h2>
    <form action="/backstage/course/save" method="post"  enctype="multipart/form-data">
        <label>
            课程id：
            <input type="text" name="cid"/>
        </label><br>
        课程文件（.md/.txt）:<br>
        <input type="file" name="file" ><br>

        <input type="submit" value="提交"/>
    </form>
<h2>删除课程</h2>
（改方法为简陋删除方法，需要课程不超过一万个知识点，否则出bug，谨慎使用）
    <form action="/backstage/course/delete" method="post">
        <label>
            课程id：
            <input type="text" name="cid"/><br>
        </label>
            <input type="submit" value="删除"/>
    </form>
</body>
<%
    String message = (String)request.getAttribute("message");
    if(message != null) {
%>
<script type="text/javascript" language="javascript">
    alert("<%= message%>");
</script>
<% } %>
</html>
