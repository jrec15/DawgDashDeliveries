<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Checkout</title>
	</head>
	<body>
		<h1>Checkout</h1>
		<p>Price: ${price}</p>
		<form action="Delivery" method="POST">
			<input type="hidden" name="task" value="SUBMIT_PAYMENT" />
			<input type="hidden" name="user_id" value="${client.iD}"/>
			Credit card number: <input type="text" name="credit_card" />
			Billing address: <input type="text" name="billing_address" />
			<input type="submit" value="Submit payment" />
		</form>
	</body>
</html>