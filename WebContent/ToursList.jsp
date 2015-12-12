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
			ArrayList<Tour> toursList = (ArrayList<Tour>) session
					.getAttribute("toursList");
			for (int i = 0; i < toursList.size(); i++) {
		%>
		<tr>
			<td><%=toursList.get(i).getTourID()%></td>
			<td><%=toursList.get(i).getDestination()%></td>
			<td><%=toursList.get(i).getDescription()%></td>
			<td><%=toursList.get(i).getPrice()%></td>
			<td><%=toursList.get(i).getStartDate()%></td>
			<td><%=toursList.get(i).getEndDate()%></td>
			<td><%=toursList.get(i).getCapacity()%></td>
			<td><%=toursList.get(i).getRating()%></td>
			<td><%=toursList.get(i).getAvailability()%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>