<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Previous Deliveries</title>
		<style>
			table {
				border: 1px solid black;
			}
			th, td {
				text-align: center;
				border: 1px solid black;
			}
		</style>
	</head>
	<body>
		
		
		<c:choose>
			<c:when test="${not empty past_deliveries}">
				<h1>Previous Deliveries</h1>
				<table>
					<tr>
						<th>Date</th>
						<th>Description</th>
					</tr>
					<c:forEach var="delivery" items="past_deliveries">
						<tr>
							<td>${delivery.timeCompleted}</td>
							<td><a href="Delivery?task=CUSTOMER_PAST_DELIVERY&delivery_id=${delivery.iD}">${delivery.clientComment}</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<h1>No previous deliveries</h1>
			</c:otherwise>
			</c:choose>
	</body>
</html>