<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Modify Account Info</title>
	</head>
	<body>
		<h1>Modify Account Info</h1>
		<h3>Change Password:</h3>
		<c:if test="${not empty password_error}">
			${password_error}
		</c:if>
		<form action="User" method="POST">
			<input type="hidden" name="task" value="CUSTOMER_CHANGE_PASSWORD" />
			Current password: <input type="password" name="old_password" /><br>
			New password: <input type="password" name="new_password" /><br>
			Verify new password: <input type="password" name="verify_password" /><br>
			<input type="submit" value="Change password" />
		</form>
		<h3>Change Email Address:</h3>
		<c:if test="${not empty email_error}">
			${email_error}
		</c:if>
		<form action="User" method="POST">
			<input type="hidden" name="task" value="CUSTOMER_CHANGE_EMAIL" />
			Password: <input type="password" name="password" /><br>
			New email address: <input type="text" name="new_email" /><br>
			Verify new email address: <input type="text" name="verify_email" /><br>
			<input type="submit" value="Change email address" />
		</form>
		<h3>Change Default Address:</h3>
		<c:if test="${not empty address_error}">
			${address_error}
		</c:if>
		<form action="User" method="POST">
			<input type="hidden" name="task" value="CUSTOMER_CHANGE_ADDRESS" />
			Password: <input type="password" name="password" /><br>
			New address: <input type="text" name="new_address" /><br>
			Verify new address: <input type="text" name="verify_address" /><br>
			<input type="submit" value="Change default address" />
		</form>
	</body>
</html>