<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Modify Account Info</title>
	</head>
	<body>
		<c:if test="${empty access_error}">
			<h1>Modify Account Info</h1>
			<h3>Change Password:</h3>
			<c:if test="${not empty password_error}">
				${password_error}
			</c:if>
			<form action="User" method="POST">
				<input type="hidden" name="task" value="WORKER_CHANGE_PASSWORD" />
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
				<input type="hidden" name="task" value="WORKER_CHANGE_EMAIL" />
				Password: <input type="password" name="password" /><br>
				New email address: <input type="text" name="new_email" /><br>
				Verify new email address: <input type="text" name="verify_email" /><br>
				<input type="submit" value="Change email address" />
			</form>
			<h3>Change Vehicle Type:</h3>
			<c:if test="${not empty vehicle_error}">
				${vehicle_error}
			</c:if>
			<form action="User" method="POST">
				<input type="hidden" name="task" value="WORKER_CHANGE_VEHICLE" />
				Password: <input type="password" name="password" /><br>
				Select largest available vehicle:
				<select name="vehicle">
					<option value="none">Select below...</option>
					<option value="bike">Bike</option>
					<option value="car">Car</option>
					<option value="truck">Truck</option>
				</select>
				<input type="submit" value="Change vehicle type" />
			</form>
		</c:if>
		<c:if test="${not empty access_error}">
			<h1>Error: Not authorized</h1>
		</c:if>
	</body>
</html>