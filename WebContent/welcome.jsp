<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Customer Welcome</title>
	</head>
	<body>
		<h1>Welcome</h1>
		<h3><a href="Delivery?task=SCHEDULE_DELIVERY&id=${client.iD}">Schedule a delivery</a></h3>
		<h3><a href="Delivery?task=CUSTOMER_PENDING_DELIVERIES&id=${client.iD}">View pending deliveries</a></h3>
		<h3><a href="Delivery?task=CUSTOMER_PAST_DELIVERIES&id=${client.iD}">View past deliveries</a></h3>
		<h3><a href="User?task=CUSTOMER_MODIFY_ACCOUNT&id=${client.iD}">Modify account info</a></h3>
	</body>
</html>
