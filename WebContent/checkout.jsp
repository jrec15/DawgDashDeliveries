<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Checkout</title>
	</head>
	<body>
		<h1>Checkout</h1>
		<p>Price: ${price}</p>
		<form action="Delivery" method="POST">
			<input type="hidden" name="task" value="SUBMIT_PAYMENT" />
			<input type="hidden" name="user_id" value="${user.iD}"/>
			Credit card number: <input type="text" name="credit_card" />
			Billing address: <input type="text" name="billing_address" />
			<input type="submit" value="Submit payment" />
		</form>
	</body>
</html>