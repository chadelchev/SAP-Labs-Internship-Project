<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="test.Tour"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSTours</title>
<link rel="shortcut icon" href="images/favicon.ico" />
</head>
<body>
	<h1>All destinations :</h1>
	<br>
	<table border="1">
		<tr>
			<td>Tour ID:</td>
			<td>Destination:</td>
			<td>Description:</td>
			<td>Price:</td>
			<td>Start Date:</td>
			<td>End Date:</td>
			<td>Capacity:</td>
			<td>Rating:</td>
			<td>Availability:</td>
		</tr>
		<%
			ArrayList<Tour> userReservedToursList = (ArrayList<Tour>) session
					.getAttribute("userReservedToursList");
			for (int i = 0; i < userReservedToursList.size(); i++) {
		%>
		<tr>
			<td><%=userReservedToursList.get(i).getTourID()%></td>
			<td><%=userReservedToursList.get(i).getDestination()%></td>
			<td><%=userReservedToursList.get(i).getDescription()%></td>
			<td><%=userReservedToursList.get(i).getPrice()%></td>
			<td><%=userReservedToursList.get(i).getStartDate()%></td>
			<td><%=userReservedToursList.get(i).getEndDate()%></td>
			<td><%=userReservedToursList.get(i).getCapacity()%></td>
			<td><%=userReservedToursList.get(i).getRating()%></td>
			<td><%=userReservedToursList.get(i).getAvailability()%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>