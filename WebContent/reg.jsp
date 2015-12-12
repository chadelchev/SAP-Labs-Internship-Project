<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="test.User"%>

<%@page import="test.WeatherServlet"%>

<%
	boolean hasUser;
	User usr = null;
	String firstName = "";
	String role = "";

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
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.container {
	width: 400px;
	height: 250px;
	clear: both;
	border-radius: 10px 10px 10px 10px;
}

.container input {
	width: auto;
	clear: both;
}

.container2 {
	width: 190px;
	height: 150px;
	clear: both;
	border-radius: 10px 10px 10px 10px; . container input { width : auto;
	clear: both;
}

body {
	background: url('images/wsMountain.jpg') no-repeat fixed /*scroll*/;
	background-size: 100% 100%;
	align: center;
	min-height: 700px;
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
				%><li class="active"><a href='reg.jsp'>Публикуване</a></li>
				<%
					} else {
				%>
				<li class="active"><a href='reg.jsp'>Регистрация</a></li>
				<%
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
							class="container2">
							<td><p align="center">
									<img src="images/weather1.png" />
								</p>
								Времето в Бургас е:<br> <%=WeatherServlet.getForecast("Бургас")%></td>
						</table></td>
					<td width="2" rowspan="4"><img src="images/color.png"
						width="2" height="600px" alt="vgreen"></td>
					<%
						if (role.equals("admin")) {
					%>
					<td width="596" rowspan="4" align="center" valgin="top"><label>
							За да добавите нова обява моля </label> <br> <label>попълнете
							следната форма:</label></br> <br>
						<form action="AddTourServlet" method="POST" enctype="multipart/form-data">
							<table class="container">
								<tr>
									<td align="right">Дестинация:</td>
									<td align="left"><input type="text" name="destination" /></td>
								</tr>
								<tr>
									<td align="right">Описание:</td>
									<td align="left"><input type="text" name="description" /></td>
								</tr>
								<tr>
									<td align="right">Цена:</td>
									<td align="left"><input type="text" name="price" /></td>
								</tr>
								<tr>
									<td align="right">Начална дата:</td>
									<td align="left"><input type="text" size=2 value="DD"
										name="startDateDay">/ <input type="text" size=2
										value="MM" name="startDateMonth">/ <input type="text"
										size=4 value="YYYY" name="startDateYear"></td>
								</tr>
								<tr>
									<td align="right">Крайна дата:</td>
									<td align="left"><input type="text" size=2 value="DD"
										name="endDateDay">/ <input type="text" size=2
										value="MM" name="endDateMonth">/ <input type="text"
										size=4 value="YYYY" name="endDateYear"></td>
								</tr>
								<tr>
									<td align="right">Капацитет:</td>
									<td align="left"><input type="text" name="capacity" /></td>
								</tr>
									<tr>
									<td align="right">Снимка:</td>
									<td align="left"><input type="file" name="picture" /></td>
								</tr>
								<tr>
									<td align="center">Публикувай</td>
									<td align="center"><INPUT type="submit" value="Добавянe"></td>
								</tr>

							</table>
						</form> <%
 	} else {
 %>
					<td width="596" rowspan="4" align="center" valign="top"><label>
							За да се регистрирате и да използвате пълната функционалност</label> <br>
						<label>на сайта моля попълнете следната форма:</label></br> <br>
						<form class="container" action="RegisterServlet" method="POST">
							<table class="container">
								<tr>
									<td align="right">Въведете първо име:</td>
									<td align="left"><input type="text" name="firstName" /></td>
								</tr>
								<tr>
									<td align="right">Въведете фамилия:</td>
									<td align="left"><input type="text" name="lastName" /></td>
								</tr>
								<tr>
									<td align="right">Въведете е-мейл:</td>
									<td align="left"><input type="text" name="eMail" /></td>
								</tr>
								<tr>
									<td align="right">Въведете телефон:</td>
									<td align="left"><input type="text" name="phone" /></td>
								</tr>
								<tr>
									<td align="right">Въведете град:</td>
									<td align="left"><input type="text" name="location" /></td>
								</tr>
								<tr>
									<td align="right">Въведете парола:</td>
									<td align="left"><input type="password" name="password" /></td>
								</tr>
								<tr>
									<td align="right">Повторете паролата:</td>
									<td align="left"><input type="password"
										name="secondPassword" /></td>
								</tr>
								<tr>
									<td align="center"></td>
									<td align="center"><INPUT type="submit"
										value="Регистрирай"></td>
								</tr>
							</table>
						</form></td>
				</tr>
				<%
					}
				%><tr>
					<td height="120" class="active"><table class="container2"
							align="center">
							<td align="center">Посетете страницата ни във фейсбук <br>
								<a
								href="https://www.facebook.com/profile.php?id=100006379088269"><img
								src="images/facebook.jpg" width="150" height="80" /></a></td>
						</table></td>
				</tr>
				<tr>
					<td height="140" class="active"><table class="container2"
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