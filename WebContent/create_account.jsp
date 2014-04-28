<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Create an Account</title>
	</head>
	<body>
		<h1>Create an Account</h1>
		<c:if test="${not empty error}">
			<p>${error}</p>
		</c:if>
		<form method="POST" action="User">
			<input type="hidden" name="task" value="CREATE_CUSTOMER_ACCOUNT" />
			First name: <input type="text" name="fname" /><br>
			Last name: <input type="text" name="lname" /><br>
			Username: <input type="text" name="username" /><br>
			Email address: <input type="text" name="email" /><br>
			Default address: <input type="text" name="address" /><br>
			Password: <input type="password" name="password" /><br>
			Verify password: <input type="password" name="password" /><br>
			<input type="submit" value="Create account" />
		</form>
	</body>
</html>