
<%@ page import="cn.lv.jdbc.DBUtils" %>
<%@ page import="java.sql.Connection" %>


<%--
  Created by IntelliJ IDEA.
  User: lvsihao
  Date: 2019-10-17
  Time: 08:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>投票系统登陆</title>
  </head>
  <body>


  <% Connection conn = DBUtils.getConnection();%>

  <%=conn%>


  <form action="/WebVote_war_exploded/loginServlet" method="post" style="margin: 0 auto;width: 500px;padding: 20px;border: 1px #cccccc solid;text-align: center;">
    用户名：<input type="text" name="username">
    <br>
    <br>
    密 码 ： <input type="text" name="password">
    <br>
    <br>
    <input type="submit">
  </form>


  </body>
</html>
