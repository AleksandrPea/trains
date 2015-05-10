<%@ page import="users.db.entities.User"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <title>Home page</title>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
  <%User user = (User) session.getAttribute("user"); %>
  <h3>Hi <%=user.getFirstName() %></h3>
  <strong>Your Email</strong>: <%=user.getEmail() %><br>
  <strong>Your Address</strong>: <%=user.getAddress() %><br>
</div>
</body>
</html>
