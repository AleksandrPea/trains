<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <title>Exception/Error Details</title>
</head>
<body>
<%
    Integer statusCode = response.getStatus();
    String servletName = request.getServerName();
    if (servletName == null) {
        servletName = "Unknown";
    }
    String requestUri = request.getRequestURI();
    if (requestUri == null) {
        requestUri = "Unknown";
    }
%>
<% if(response.getStatus() != 500) { %>
    <div class="container">
        <h3>Error Details</h3>
        <strong>Status Code</strong>:<%=statusCode%><br>
        <strong>Requested URI</strong>:<%=requestUri%><br>
    </div>
<%}else {%>
    <h3>Exception Details</h3>
    <ul class="list-group">
        <li class="list-group-item">Servlet Name:<%=servletName%></li>
        <li class="list-group-item">Exception Name:<%=exception.getClass().getName()%></li>
        <li class="list-group-item">Requested URI:<%=requestUri%></li>
        <li class="list-group-item">Exception Message:<%=exception.getMessage()%></li>
    </ul>
<%}%>
<br>
<a href="login.html">Login Page</a>
</body>
</html>
