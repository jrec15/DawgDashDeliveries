<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Delivery</title>
	</head>
	<body>
		<c:if test="${empty delivery}">
			<h1>Error: No such delivery or not authorized</h1>
		</c:if>
		<c:if test="${not empty delivery}">
			<h1>Delivery ${delivery.iD}</h1>
			<p>Description: ${delivery.clientComment}</p><br>
			<p>Date: ${delivery.timeCompleted}</p><br>
			<p>Price: ${delivery.price}</p><br>
			<p>Carrier comments: ${delivery.workerComment}</p>
		</c:if>
	</body>
</html>