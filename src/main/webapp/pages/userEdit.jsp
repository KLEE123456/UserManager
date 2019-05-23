
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
%>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>Edit.jsp</title>
    <script src="<%=path%>/js/jquery-3.3.1.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            document.getElementById("userSex${user.userSex}").checked="checked";
        });

        $(function () {
            var userName=$("#userName").val();
            $("#userName").blur(function () {
                var userNames=$("#userName").val();
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/user/checkName.action',
                    data:{userName:userNames},
                    success:function (data) {
                        if (data==1){
                            $("#msg").text("用户名检测成功,可以修改!");
                            document.getElementById("regbtn").disabled=false;
                        }
                        if (data==0){
                           if (userName==$("#userName").val()){
                               $("#msg").text("用户名检测成功,可以修改!");
                               document.getElementById("regbtn").disabled=false;
                           }
                           else {
                                $("#msg").text("用户名已存在,请更换!");
                               document.getElementById("regbtn").disabled=true;
                           }
                        }
                    }
                })
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
    <form action="${pageContext.request.contextPath}/user/userEdit.action" method="post" id="form" name="form" style="background-color: aliceblue">
        <h2>用户编辑界面</h2>
        <input type="hidden" name="userId" id="userId" value="${user.userId}">
        <p></p>
        <font>用户名:</font>
        <input type="text" name="userName"  id="userName" value="${user.userName}">
        <p/>
        &nbsp;<font>密码:</font>
        <input type="password" name="userPwd"  id="userPwd" value="${user.userPwd}">
        <p/>
        &nbsp;<font>电话:</font>
        <input type="text" name="userPhone"  id="userPhone" value="${user.userPhone}">
        <p/>
        <font>性别:</font> &nbsp;
        <input type="radio" name="userSex" id="userSex男" value="男"><label for="userSex男">男</label>
        <input type="radio" name="userSex" id="userSex女" value="女"><label for="userSex女">女</label>
        <p/>
        <input type="button" value="确定修改" id="regbtn" name="regbtn">
    </form>
        <span id="msg" style="color: red">${msg}</span>
    </center>
</body>
</html>
