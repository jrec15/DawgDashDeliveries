<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Dawg Dash Deliveries</title>
	</head>
	<body>
		<h1>Dawg Dash Deliveries</h1>
		<h3><a href="User?task=customer_create_account">Create an Account</a></h3>
		<h3>Login:</h3>
		<c:if test="${not empty error}">
			<p>${error}</p>
		</c:if>
		<%-- POST request to avoid displaying login information in URL --%>
		<form method="POST" action="User">
			<input type="hidden" name="task" value="LOGIN" />
			Username: <input type="text" name="loginId" /><br>
			Password: <input type="password" name="password" /><br>
			<input type="submit" value="Login" />
		</form>
		<h3>Get an Estimate:</h3>
		<form method="GET" action="Delivery">
			<input type="hidden" name="task" value="ESTIMATE" />
			Largest item:
			<select name="item_size">
				<option value="not_selected">Select size...</option>
				<option value="letter">Letter/Document</option>
				<option value="small">Small package (~1 sq foot)</option>
				<option value="large">Large package</option>
			</select><br>
			Quantity: <input type="text" name="quantity" /><br>
			<input type="submit" value="Get Estimate" />
		</form>
		<c:if test="${not empty estimate}">
			<p>${estimate.price}</p>
		</c:if>
	</body>
</html>
