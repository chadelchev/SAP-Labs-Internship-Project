<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="test.User"%>
<%@ page import="test.Tour"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@page import="test.WeatherServlet"%>
<%
	boolean hasUser;
	boolean hasReservations;
	User usr = null;
	String firstName = "";
	String role = "";
	ArrayList<Tour> userReservedToursList = (ArrayList<Tour>) session
			.getAttribute("userReservedToursList");

	if (session.getAttribute("userReservedToursList") == null) {
		hasReservations = false;
	} else {
		hasReservations = true;
	}

	if (session.getAttribute("user") == null) {
		hasUser = false;
	} else {
		hasUser = true;
		usr = (User) session.getAttribute("user");
		firstName = usr.getFirstName();
		role = usr.getRole();
	}
%>
<!doctype html>

<html>
<head>
<link rel="stylesheet" type="text/css" href="mystyles.css"
	media="screen" />
<title>CSTours</title>
<link rel="shortcut icon" href="images/favicon.ico" />
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1251">
<style type="text/css">
body {
	background: url('images/bb.jpg') no-repeat fixed /*scroll*/;
	background-size: 100% 100%;
	align: center;
	min-height: 700px;
}

.container {
	width: 190px;
	height: 150px;
	clear: both;
	border-radius: 10px 10px 10px 10px; . container input { width : auto;
	clear: both;
}
</style>
</head>
<body>

	<div align="center">
		<div id='cssmenu'>
			<ul>
				<li><span><img src="images/newlogo.png" height="60px" /><span></li>
				<li><a href='index.jsp'><span>Начало </span></a></li>
				<li><a href='destinations.jsp'><span>Дестинации</span></a></li>
				<li><a href='forus.jsp'><span>За нас</span></a></li>
				<li><a href='contacts.jsp'><span>Контакти</span></a></li>
				<%
					if (role.equals("admin")) {
				%><li><a href='reg.jsp'>Публикуване</a></li>
				<%
					} else {
						if (role.equals("user")) {
				%>
				<li><a href='userDestinations.jsp'>Моите Турове</a></li>
				<%
					} else {
				%>
				<li><a href='reg.jsp'>Регистрация</a></li>

				<%
					}
					}
				%>

			</ul>
		</div>
		<font class="classname">
			<table height="600" border="0" align="center" cellspacing="0">
				<%
					if (!hasUser) {
				%>
				<tr>
					<td height="20" colspan="3" align="left" valign="baseline">
						<form action="LogInServlet" method="post" class="form">
							Email: <input type="text" name="eMail"> Password: <input
								type="password" name="password"> <input type="submit"
								value="Login">
						</form>
					</td>
					<td></td>
				</tr>
				<%
					} else {
						if (role.equals("user")) {
				%>
				<td height="20" colspan="3" align="left" valign="baseline">
					<form action="LogOutServlet" method="post" class="form">
						Добре дошъл:
						<%=usr.getFirstName()%>
						<input type="submit" value="LogOut">
					</form> <%
 	} else {
 			if (role.equals("admin")) {
 %>
				
				<td height="20" colspan="3" align="left" valign="baseline">
					<form action="LogOutServlet" method="post" class="form">
						Добре дошъл:
						<%=firstName%>
						(Admin) <input type="submit" value="LogOut">
					</form> <%
 	}

 		}
 	}
 %>
				
				<tr>
					<td height="2" colspan="3"><img src="images/color.png"
						width="100%" height="2" alt="greed"></td>
				</tr>
				<tr>
					<td width="190" height="120" class="active" align="center"><table
							class="container">
							<td>Времето в София е:<br> <%=WeatherServlet.getForecast("София")%></td>
						</table></td>
					<td width="2" rowspan="4"><img src="images/color.png"
						width="2" height="600px" alt="vgreen"></td>
					<%
						if (role.equals("admin")) {
					%>
					<td width="596" rowspan="4" valign="top">Кликнете за да видите
						регистрираните потребители!
						<form action="users.jsp" method="post" class="form">
							<input type="submit" value="Users">
						</form>
					</td>
					<%
						} else {
					%>
					<td width="596" rowspan="4" align="center" valign="top">
						<h3><%=session.getAttribute("empty")%></h3>
					</td>
					<%
						}
					%>
				</tr>
				<tr>
					<td height="120" class="active"><table class="container"
							align="center">
							<td align="center">Посетете страницата ни във фейсбук <br>
								<img src="images/facebook.jpg" width="150" height="80" /></td>
						</table></td>
				</tr>
				<tr>
					<td height="140" class="active"><table class="container"
							align="center">

							<td align="center"><p>Телефони за връзка:</p>
								<p>+359 888 764 901</p>
								<p>+359 888 764 902</p></td>


						</table></td>
				</tr>
				<tr>
					<td width="196" height="120" class="active"></td>
				</tr>
			</table>
	</div>
</body>
</html>