<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Delivery</title>
	</head>
	<body>
		<h1>Delivery ${delivery.iD}</h1>
		<h3>Add a comment:</h3>
			<c:if test="${not empty error}">
				${error}
			</c:if>
			<form action="Delivery" method="POST">
				<input type="hidden" name="task" value="WORKER_COMMENT_DELIVERY" />
				<input type="hidden" name="delivery_id" value="${delivery.iD}" />
				<input type="text" name="comment" />
				<input type="submit" value="Submit comment" />
			</form>
		<h3>Mark delivery as uncompletable:</h3>
		<form action="Delivery" method="POST">
			<input type="hidden" name="UNCOMPLETABLE" value="1" />
			<input type="hidden" name="delivery_id" value="${delivery.iD}" />
			<input type="submit" value="Report failed delivery" />
		</form>
	</body>
</html>