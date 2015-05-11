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
  <title>View page</title>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
  <h3>Profile</h3>
  <div><strong>First Name</strong>: ${requestScope.reqUser.firstName}</div>
  <div><strong>Last Name</strong>: ${requestScope.reqUser.lastName}</div>
  <div><strong>Email</strong>: ${requestScope.reqUser.email}</div>
  <div><strong>Address</strong>: ${requestScope.reqUser.address}</div>
  <c:if test="${requestScope.reqCarrier != null}">
    <div><strong>Tariff</strong>: ${requestScope.reqCarrier.tariff}</div>
    <div><strong>Info</strong>: ${requestScope.reqCarrier.info}</div>
    <div>
      <strong>Asociated stations</strong>:
      <c:forEach items="${requestScope.reqCarrier.stations}" var="station">
        ${station.name}
      </c:forEach>
    </div>
  </c:if>
  <div><strong>Date of registration</strong>: ${requestScope.reqUser.create_date}</div>
</div>
</body>
</html>
