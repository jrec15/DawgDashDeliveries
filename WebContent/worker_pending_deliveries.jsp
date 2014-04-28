<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Assigned Deliveries</title>
		<style>
			table {
				border: 1px solid black;
			}
			tr, th {
				text-align: center;
				border: 1px solid black;
			}
		</style>
	</head>
	<body>
		<h1>Assigned Deliveries</h1>
		<c:if test="${empty pending_deliveries}">
			<h3>No pending deliveries</h3>
		</c:if>
		<c:if test="${not empty pending_deliveries}">
			<table>
				<tr>
					<th>Description</th>
					<th>Pick-up time</th>
					<th>Pick-up address</th>
					<th>Destination address</th>
				</tr>
				<c:forEach var="delivery" items="${pending_deliveries}">
					<tr>
						<td>
							<a href="Delivery?task=VIEW_DELIVERY&delivery_id=${delivery.iD}">
								${delivery.clientComment}
							</a>
						</td>
						<td>${delivery.pickUpTime}</td>
						<td>${delivery.sourceAddress}</td>
						<td>${delivery.destinationAddress}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<h3><a href="User?task=WORKER_MODIFY_ACCOUNT">Modify account info</a></h3>
	</body>
</html>