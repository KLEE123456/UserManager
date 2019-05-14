
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
%>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Register</title>
    <script src="<%=path%>/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#userName").blur(function () {
                var userName=$("#userName").val();
                if (userName==''){
                    alert('请输入用户名!');
                }
                else {
                    $.ajax({
                        type:'post',
                        url:'<%=path%>/user/checkName.action',
                        data:{userName:userName},
                        success:function (data) {
                            if (data==1){
                                $("#myspan").text("用户名检测成功，可注册!");
                                document.getElementById("regbtn").disabled=false;
                            }
                            if (data==0){
                                $("#myspan").text("用户名已存在，请更改!");
                                document.getElementById("regbtn").disabled=true;
                            }
                        }
                    })
                }
            })
            $("#regbtn").click(function () {
                var userName=$("#userName").val();
                var userPwd=$("#userPwd").val();
                var userPhone=$("#userPhone").val();
                var userSex=$("input[name='userSex']:checked").val();
                if (userName==''){
                    $("#msg").text("请输入用户名!");
                    return;
                }
                if (userPwd==''){
                    $("#msg").text("请输入密码!");
                    return;
                }
                if (userPhone==''){
                    $("#msg").text("请输入电话号码!");
                    return;
                }
                if (userSex==null){
                    $("#msg").text("请选择性别!");
                    return;
                }
                $("#form").submit();

            })
        })
    </script>
</head>
<body>
<center>
    <form action="<%=path%>/user/register.action" method="post" id="form" name="form" style="background-color: aliceblue">
        <h2>用户注册界面</h2>
        <p></p>
        <font>用户名:</font>
        <input type="text" name="userName"  id="userName">
        <p/>
        <span id="myspan" style="color: red"></span>
        <p/>
        &nbsp;<font>密码:</font>
        <input type="password" name="userPwd"  id="userPwd">
        <p/>
        &nbsp;<font>电话:</font>
        <input type="text" name="userPhone"  id="userPhone">
        <p/>
        <font>性别:</font> &nbsp;
        <input type="radio" name="userSex" id="userSex男" value="男"><label for="userSex男">男</label>
        <input type="radio" name="userSex" id="userSex女" value="女"><label for="userSex女">女</label>
        <p/>
        <span id="msg" style="color: red;"></span>
        <input type="button" value="确定注册" id="regbtn" name="regbtn">
        <p>
            <span id="result">${reg}</span>
        </p>
    </form>
</center>
</body>
</html>
