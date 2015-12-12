<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="test.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CSTours</title>
<link rel="shortcut icon" href="images/favicon.ico" />
</head>
<body>
	<h1 align="center">Регистрирани потребители:</h1>
	<br>
	<table align="center" border="1">
		<tr>
			<td>Първо име</td>
			<td>Фамилно име</td>
			<td>E-mail</td>
			<td>Телефон</td>
			<td>Град</td>

		</tr>
		<%
			ArrayList<User> registeredUsersList = (ArrayList<User>) session
					.getAttribute("registeredUsersList");
			for (int i = 0; i < registeredUsersList.size(); i++) {
		%>
		<tr>
			<td><%=registeredUsersList.get(i).getFirstName()%></td>
			<td><%=registeredUsersList.get(i).getLastName()%></td>
			<td><%=registeredUsersList.get(i).geteMail()%></td>
			<td><%=registeredUsersList.get(i).getPhone()%></td>
			<td><%=registeredUsersList.get(i).getLocation()%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>