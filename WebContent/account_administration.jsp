<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Account Administration</title>
	</head>
	<body>
		<c:if test="${empty access_error}">
			<h1>Account Administration</h1>
			<h3>Change Someone's Password</h3>
			<c:if test="${not empty password_error}">
				${password_error}
			</c:if>
			<form action="User" method="POST">
			<input type="hidden" name="task" value="ADMIN_CHANGE_PASSWORD" />
				Worker: 
				<select name="worker">
					<option value="none">Select a worker...</option>
					<c:forEach var="worker" items="${workers}">
						<option value="${worker.iD}">${worker.name}</option>
					</c:forEach>
				</select><br>
				Manager password: <input type="password" name="manager_password" /><br>
				New password: <input type="password" name="new_password" /><br>
				Verify new password: <input type="password" name="verify_password" /><br>
				<input type="submit" value="Change password" />
			</form>
			<h3>Add New Worker</h3>
			<c:if test="${not empty new_worker_error}">
				${new_worker_error}
			</c:if>
			<form action="User" method="POST">
				<input type="hidden" name="task" value="ADMIN_CREATE_USER" />
				Worker name: <input type="text" name="worker_name" /><br>
				Worker email address: <input type="text" name="worker_email" /><br>
				Worker username: <input type="text" name="worker_username" /><br>
				Worker password: <input type="password" name="worker_password" /><br>
				Verify worker password: <input type="password" name="verify_worker_password" /><br>
				<select name="transportation">
					<option value="none">Select largest available transportation</option>
					<option value="bike">Bike</option>
					<option value="car">Car</option>
					<option value="truck">Truck</option>
				</select>
				<input type="submit" value="Create worker" />
			</form>
		</c:if>
		<c:if test="${not empty access_error}">
			<h1>Error: Not authorized</h1>
		</c:if>
	</body>
</html>