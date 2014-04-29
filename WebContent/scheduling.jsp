<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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