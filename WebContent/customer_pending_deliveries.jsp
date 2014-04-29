<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Pending Deliveries</title>
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
		<h1>Pending Deliveries</h1>
		<c:choose>
			<c:when test="${not empty pending_deliveries}">
				<table>
					<tr>
						<th>Description</th>
						<th>Pick-up time</th>
						<th>Price</th>
						<th>Cancel delivery</th>
					</tr>
					<c:forEach var="delivery" items="${pending_deliveries}">
						<tr>
							<td>${delivery.clientComment}</td>
							<td>${delivery.pickupTime}</td>
							<td>${delivery.price}</td>
							<td>
								<form action="Delivery" method="POST">
									<input type="hidden" name ="task" value="CANCEL_DELIVERY" />
									<input type="hidden" name = "delivery_id" value="${delivery.iD}" />
									<input type="submit" value="Cancel delivery" />
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
				<h3>Total price: ${price}</h3>
				<a href="Delivery?task=CHECKOUT">Checkout</a>
			</c:when>
			<c:otherwise>
				<h3>No pending deliveries</h3>
			</c:otherwise>
		</c:choose>
	</body>
</html>