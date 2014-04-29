<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
