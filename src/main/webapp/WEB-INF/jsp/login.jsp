<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    String path=request.getContextPath();
%>
<%@page isELIgnored="false" %>
<html>
<head>
    <link href="<%=path%>/bootstrap/bootstrap.css" rel="stylesheet"/>
    <link href="<%=path%>/bootstrap-table/bootstrap-table.css" rel="stylesheet"/>
    <script src="<%=path%>/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script src="<%=path%>/bootstrap/bootstrap.js" type="text/javascript"></script>
    <script src="<%=path%>/bootstrap-table/bootstrap-table.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#loginbtn").click(function () {
                var  name=$("#userName").val();
                var  pwd=$("#userPwd").val();
                if (name==""){
                   $("#message").text("请输入用户名!");
                    return;
                }
                if (pwd==""){
                   $("#message").text("请输入密码!");
                    return;
                }
                $("#form").submit();
            })

        });
    </script>
    <style type="text/css">
        div{
            width: 400px;
            height: 500px;
            margin: 0 auto;
            background-color:#b1dfbb;
            border-radius: 10px;

        }
    </style>
</head>
<body>
<div>
    <center><h3 style="color: #005cbf">用户登录界面</h3></center>
    <center>
        <form action="${pageContext.request.contextPath}/user/login.action" method="post" id="form" name="form">
            <table  width="300px" >
                <tr height="70px">
                    <td>用户名:</td>
                    <td>
                        <input type="text" id="userName" name="userName" value="${userName}" >
                    </td>
                </tr>
                <tr>
                    <td>密&nbsp;&nbsp;&nbsp;码:</td>
                    <td>
                        <input type="password" id="userPwd" name="userPwd">
                    </td>
                </tr>
                <tr height="30px" align="center">
                    <td colspan="2">
                        <span id="message" style="color: red">${msg}</span>
                    </td>
                </tr>
                <tr >
                    <td colspan="2">
                        <input type="button" value="登录" id="loginbtn" name="loginbtn" style="width: 100%;background-color: #005cbf;color: white">
                    </td>
                </tr>
                <tr  align="right">
                    <td colspan="2">
                        <a href="${pageContext.request.contextPath}/user/toRegister.action" style="text-decoration: none;color:olive"><i>注册</i>>>></a>
                    </td>

                </tr>
            </table>
        </form>

    </center>
</div>

</body>
</html>
