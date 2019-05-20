
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<% String path=request.getContextPath();%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<html>
<head>
    <title>Welcome</title>
    <script src="<%=path%>/js/jquery-3.3.1.js" type="text/javascript"></script>
    <link href="<%=path%>/bootstrap/bootstrap.css" rel="stylesheet"/>
    <link href="<%=path%>/bootstrap-table/bootstrap-table.css" rel="stylesheet"/>
    <script src="<%=path%>/bootstrap/bootstrap.js" type="text/javascript"></script>
    <script src="<%=path%>/bootstrap-table/bootstrap-table.js" type="text/javascript"></script>
    <script type="text/javascript">
        function  checkDel(userName,userId,pageNum) {
            if(confirm("你确定删除"+userName+"这条记录吗?")){
                location.href='${pageContext.request.contextPath}/user/deleteUser.action?userId='+userId+'&pageNum='+pageNum;
            }
        }
        $(function () {
            $("#addId").click(function () {
                location.href='/pages/add.jsp';
            })
            $("#rlogin").click(function () {
                $("#form1").submit();
            })
            $("#btn").click(function () {
                var adminName=$("#adminName").val();
                if (adminName==''){
                    alert('请输入用户名!');
                    return;
                }
                $("#form").submit();
            })
        })
    </script>
    <style type="text/css">
        .div1{
            width: 380px;
            height: 50px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<center><h2>***************欢迎进入系统***************</h2></center>

<table width="90%" align="center"  class="table-striped table-bordered" id="talbe">
    <tr>
        <td colspan="6"><i style="color: #005cbf">~hi,亲爱的<font color="#ff7f50">${userMsg.userName}</font></i></td>
        <td>
            <input type="button" value="新增信息" name="add" id="addId" style="background-color:#0062cc;width:100%;color: white" >
        </td>
    </tr>
    <tr>
        <td colspan="7">
            <form action="selectAll.do" method="post" id="form" name="form" style="border: 0px;margin: 0px">
                <input type="text" placeholder="请输入用户名" id="adminName" name="adminName">
                <input type="button" value="查询" id="btn" name="btn" style="background-color:#0062cc;width:60px;color: white">
                <input type="hidden" value="flag" id="flagId" name="flagName">
            </form>
        </td>
    </tr>
    <tr align="center" bgcolor="#ffebcd" height="30px">
        <td>序号</td>
        <td>用户id</td>
        <td>用户姓名</td>
        <td>用户电话</td>
        <td>用户密码</td>
        <td>用户性别</td>
        <td>操作</td>
    </tr>
    <c:choose>
        <c:when test="${userList.size()==0}">
            <tr>
                <td colspan="7" height="50px" align="center">
                    <b>&lt;不存在用户信息!&gt;</b>
                </td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach items="${userList}" varStatus="status" var="user">
                <tr align="center" bgcolor="#ffebcd">
                    <td align="center">${status.count}</td>
                    <td align="center">${user.userId}</td>
                    <td align="center">${user.userName}</td>
                    <td align="center">${user.userPwd}</td>
                    <td align="center">${user.userSex}</td>
                    <td align="center">${user.userPhone}</td>
                    <td align="center">
                        <a href="${pageContext.request.contextPath}/user/editXr.action?userId=${user.userId}&pageNum=${page.pageNum}">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;

                        <a href="javascript:void(0)" onclick="checkDel('${user.userName}','${user.userId}','${page.pageNum}')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>
<p/>
<form id="form1" name="form1" action="<%=path%>/user/userQuit.action" method="post">
    <input type="button" id="rlogin" name="rlogin" value="安全退出" style="width: 100px;background-color: beige;color:olivedrab;margin-left: 5%">
</form>
<div class="div1" style="text-align: center">
    <font style="align:center">当前页是第${page.pageNum}页，总共${page.pages}页，总记录数${page.total}条</font>
</div>

<!--boostrap4 版本-->
<div class="div1" style="padding-left: 60px">
    <ul class="pagination" >
        <li class="page-item"><a class="page-link" href="<%=path%>/user/findAllUser.action?pageNum=1">首页</a></li>
        <li class="page-item" id="upPage"><a  class="page-link" href="<%=path%>/user/findAllUser.action?pageNum=${page.prePage}">上一页</a></li>
        <li class="page-item" id="downPage"><a  class="page-link" href="<%=path%>/user/findAllUser.action?pageNum=${page.nextPage}">下一页</a></li>
        <li class="page-item"><a class="page-link" href="<%=path%>/user/findAllUser.action?pageNum=${page.pages}">尾页</a></li>
    </ul>
</div>
</body>
</html>
