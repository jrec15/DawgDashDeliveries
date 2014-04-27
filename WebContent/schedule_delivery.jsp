<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Schedule a Delivery</title>
	</head>
	<body>
		<h1>Schedule a Delivery</h1>
		<c:if test="${not empty error}">
			${error}
		</c:if>
		<form action="Delivery" method="POST">
			<input type="hidden" name="task" value="SCHEDULE_DELIVERY"/>
			Pick-up address: <input type="text" name="pickup_address" /> (Leave blank to use your default address)<br>
			Destination address: <input type="text" name="destination_address" /><br>
			Number of packages: <input type="text" name="quantity" /><br>
			Largest package size:
			<select name="size">
				<option value="none">Select below...</option>
				<option value="letter">Letter/Document</option>
				<option value="small">Small package (~1 sq foot)</option>
				<option value="large">Large package</option>
			</select><br>
			Brief description: <input type="text" name="description" /><br>
			Pick-up time:
			<select name="time">
				<option value="none">Select below...</option>
				<option value="0800">08:00 am</option>
				<option value="0830">08:30 am</option>
				<option value="0900">09:00 am</option>
				<option value="0930">09:30 am</option>
				<option value="1000">10:00 am</option>
				<option value="1030">10:30 am</option>
				<option value="1100">11:00 am</option>
				<option value="1130">11:30 am</option>
				<option value="1200">12:00 pm</option>
				<option value="1230">12:30 pm</option>
				<option value="1300">01:00 pm</option>
				<option value="1330">01:30 pm</option>
				<option value="1400">02:00 pm</option>
				<option value="1430">02:30 pm</option>
				<option value="1500">03:00 pm</option>
				<option value="1530">03:30 pm</option>
				<option value="1600">04:00 pm</option>
				<option value="1630">04:30 pm</option>
				<option value="1700">05:00 pm</option>
				<option value="1730">05:30 pm</option>
			</select><br>
			<input type="submit" value="Schedule delivery" />
		</form>
	</body>
</html>