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
  <h3>Welcome ${sessionScope.user.firstName} ${sessionScope.user.lastName}</h3>
  <strong>Your Email</strong>: ${sessionScope.user.email}<br>
  <strong>Your Address</strong>: ${sessionScope.user.address}<br>
  <c:if test="${sessionScope.carrier != null}">
    <strong>Tariff</strong>: ${sessionScope.carrier.tariff}<br>
    <strong>Info</strong>: ${sessionScope.carrier.info}<br>
  </c:if>
  <strong>Date of registration</strong>: ${sessionScope.user.create_date}<br><br>
  <a href="edit.jsp" class="btn btn-default">
    <span class="glyphicon glyphicon-edit"></span> Edit
  </a>
</div>
</body>
</html>
