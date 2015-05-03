<%@ page import="users.db.entities.User"%>
<%@ page import="java.util.Set" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <title>Home Page</title>
</head>
<body>
<%
    Set<String> categories = (Set<String>) session.getAttribute("categories");
    pageContext.setAttribute("isCarrier", categories.contains("Carrier"));
%>
<%User user = (User) session.getAttribute("user"); %>
<h3>Hi <%=user.getFirstName() %></h3>
<strong>Your Email</strong>: <%=user.getEmail() %><br>
<strong>Your Address</strong>: <%=user.getAddress() %><br>
<c:if test="${isCarrier}">
    <p>You are a carrier! Please, choose your stations: <a href="carrier/choose.jsp">here</a></p>
</c:if>
<br>
<form action="Logout" method="post">
  <input type="submit" value="Logout" >
</form>
</body>
</html>
