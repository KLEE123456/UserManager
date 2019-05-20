<%--
  Created by IntelliJ IDEA.
  User: KLEE
  Date: 2019/5/20
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
%>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>testJson.jsp</title>
    <script src="<%=path%>/js/jquery-3.3.1.js"></script>
    <script type="text/javascript">
       $(function () {
           $("#testJson").click(function () {
               var userName=$("#userName").val();
               var userPwd=$("#userPwd").val();
              $.ajax({
                  type:"post",
                  url:"${pageContext.request.contextPath}/user/testJson.action",
                  data:JSON.stringify({userName:userName,userPwd:userPwd}),
                  contentType:"application/json;charset=UTF-8",
                  dataType:"json",
                  success:function (data) {
                      alert(data.userName+","+data.userPwd);
                  }
              })
           })
       })
    </script>

</head>
<body>
    <form>
        <input type="text"  name="userName" id="userName">
        </p>
        <input type="password" id="userPwd"  name="userPwd">
        </p>
        <input type="button" id="testJson" value="提交" >
    </form>
</body>
</html>
