<%@ page import="ORMroad.Station" %>
<%@ page import="java.util.List" %>
<%@ page import="ORMroad.Database" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <title>Choosing stations</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
  List<Station> stations = (List<Station>) Database.getStation("");
  pageContext.setAttribute("stations", stations);
%>
<h1>Hello, world!</h1>
<div class="form-group">
  <label for="sel">Select list:</label>
  <select name="stations" multiple class="form-control" id="sel">
    <c:forEach items="${stations}" var="station">
      <option value="${station.stationId}">${station.name}</option>
    </c:forEach>
  </select>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>
