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
  <title>Search page</title>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
  <div class="row">
    <form action="Search" method="get" role="form">
      <div class="form-group">
        <div class="col-sm-8">
          <input type="text" name="search" class="form-control"
                 placeholder="Input station name here">
        </div>
      </div>
      <div class="col-sm-4">
        <button type="submit" value="Search" class="btn btn-info">
          <span class="glyphicon glyphicon glyphicon-search"></span> Search
        </button>
      </div>
    </form>
  </div>
  <c:if test="${requestScope.reqUsers != null}">
    <h3>${requestScope.count} carriers were found:</h3>
    <div class="list-group col-sm-8">
    <c:forEach items="${requestScope.reqUsers}" var="user">
      <a href="View?userId=${user.id}" class="list-group-item">
        <h4 class="list-group-item-heading">${user.firstName}</h4>
        <p class="list-group-item-text">${user.email}</p>
      </a>
    </c:forEach>
    </div>
  </c:if>
</div>
</body>
</html>
