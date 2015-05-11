<%@ page import="ORMroad.Station" %>
<%@ page import="java.util.List" %>
<%@ page import="ORMroad.Database" %>
<%@ page import="users.db.entities.Carrier" %>
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
  <title>Manage stations</title>
</head>
<body>
<%
    List<Station> carStations = ((Carrier) session.getAttribute("carrier")).getStations();
    List<Station> stations = Database.getFilteredStation("", carStations);
    pageContext.setAttribute("stations", stations);
%>
<%@include file="/header.jsp"%>
<div class="container">
  <div class="row">
    <form action="StationsSelect" method="post" role="form">
      <div class="col-sm-6">
        <div class="form-group">
          <label for="sel1">Select stations from this list:</label>
          <select name="stations" multiple class="form-control" size="15" id="sel1">
            <c:forEach items="${stations}" var="station">
              <option value="${station.stationId}">${station.name}</option>
            </c:forEach>
          </select>
        </div>
        <button type="submit" value="SelectStations" class="btn btn-default">Add stations</button>
      </div>
    </form>
    <form action="StationsDelete" method="post" role="form">
      <div class="col-sm-6">
        <div class="form-group">
          <label for="sel2">Select stations for delete:</label>
          <select name="carrierStations" multiple class="form-control" size="15" id="sel2">
            <c:forEach items="${sessionScope.carrier.stations}" var="station">
              <option value="${station.stationId}">${station.name}</option>
            </c:forEach>
          </select>
        </div>
        <button type="submit" value="DeleteStations" class="btn btn-default">Delete stations</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>
