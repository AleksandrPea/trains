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
  <title>Edit page</title>
</head>
<body>
<%@include file="header.jsp"%>
<div class="container">
<h3>Edit profile</h3><br>
<form action="Edit" method="post" id="usrform" class="form-horizontal" role="form">
  <div class="form-group">
    <label class="control-label col-sm-2" for="lastName">Last name:</label>
    <div class="col-sm-10">
      <input type="text" name="lastName" id="lastName" class="form-control"
             value="${sessionScope.user.lastName}">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="firstName">First name:</label>
    <div class="col-sm-10">
      <input type="text" name="firstName" id="firstName" class="form-control"
             value="${sessionScope.user.firstName}">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="address">Address:</label>
    <div class="col-sm-10">
      <input type="text" name="address" id="address" class="form-control"
             value="${sessionScope.user.address}">
    </div>
  </div>
  <c:if test="${sessionScope.carrier != null}">
    <div class="form-group">
      <label class="control-label col-sm-2" for="tariff">Tariff:</label>
      <div class="col-sm-10">
        <input type="text" name="tariff" id="tariff" class="form-control"
               value="${sessionScope.carrier.tariff}">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="info">Additional information:</label>
      <div class="col-sm-10">
        <textarea name="info" form="usrform" id="info" class="form-control" rows="5">${sessionScope.carrier.info}
        </textarea>
      </div>
    </div>
  </c:if>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" value=Edit" class="btn btn-default">Submit</button>
    </div>
  </div>
</form>
<br>
</div>
</body>
</html>
