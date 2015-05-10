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
  <title>Header</title>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Train timetable</a>
    </div>
    <div>
      <ul class="nav navbar-nav">
        <li><a href="/1111/home.jsp">Home</a></li>
        <li><a href="#">Page 1</a></li>
        <li><a href="#">Page 2</a></li>
        <c:if test="${sessionScope.carrier != null}">
          <li>
            <a href="/1111/carrier/manage.jsp">Manage stations <sup><span class="label label-info">Carrier</span></sup></a>
          </li>
        </c:if>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li>
          <form action="/1111/Logout" method="post" role="form">
            <button type="submit" value="Logout" class="btn btn-default btn-lg">
              <span class="glyphicon glyphicon-log-out"></span> Logout
            </button>
          </form>
        </li>
      </ul>
    </div>
  </div>
</nav>
</body>
</html>