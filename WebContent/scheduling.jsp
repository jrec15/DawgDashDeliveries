<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>User Scheduling</title>
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
		<c:if test="${empty access_error}">
			<h1>User Scheduling</h1>
			<table>
				<tr><th>Worker</th></tr>
				<c:forEach var="worker" items="${workers}">
					<tr><td><a href="User?task=INDIVIDUAL_SCHEDULE&worker_id=${worker.iD}">${worker.name}</a></td></tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${not empty access_error}">
			<h1>Error: Not authorized</h1>
		</c:if>
	</body>
</html>