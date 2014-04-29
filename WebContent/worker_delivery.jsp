<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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