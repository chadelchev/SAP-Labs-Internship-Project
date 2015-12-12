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
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1251">
<style type="text/css">
.container {
	width: 500px;
	height: 300px;
	clear: both;
	border-radius: 10px 10px 10px 10px;
}

.container input {
	width: auto;
	clear: both;
}

.textarea {
	resize: none;
}

.container2 {
	width: 190px;
	height: 150px;
	clear: both;
	border-radius: 10px 10px 10px 10px; . container input { width : auto;
	clear: both;
}

body {
	background: url('images/beach.jpg') no-repeat fixed /*scroll*/;
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
				<li class="active"><a href='contacts.jsp'><span>Контакти</span></a></li>
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
							class="container2">
							<td><p align="center">
									<img src="images/weather1.png" />
								</p>
								Времето в Пловдив е:<br> <%=WeatherServlet.getForecast("Пловдив")%></td>
						</table></td>
					<td width="2" rowspan="4"><img src="images/color.png"
						width="2" height="600px" alt="vgreen"></td>

					<td width="596" rowspan="4" align="center" valign="top"><label>За
							да се свържете с нас моля пишете ни</label><br> <br>
						<form action="MailServlet" method='post'>

							<table class="container" align="center">
								<tr>
									<td width='100px'><label>From:</label></td>
									<td><input type='text' size='40' value=''
										name='fromaddress'></td>
								</tr>
								<tr>
									<td><label>To:</label></td>
									<td><input type='text' size='40'
										value='CSToursSAP@gmail.com' name='toaddress'
										readonly="readonly"></td>
								</tr>
								<tr>
									<td><label>Subject:</label></td>
									<td><textarea rows='1' cols='45' name='subjecttext'
											class="textarea">Обект на писмото</textarea></td>
								</tr>
								<tr>
									<td><label>Mail:</label></td>
									<td><textarea rows='7' cols='45' name='mailtext'
											class="textarea">Съобщение</textarea></td>
								</tr>
								<tr>
								<tr>
									<td align="center"><input type='submit' value='Send Mail'></td>
								</tr>
							</table>
						</form></td>
				</tr>
				<tr>
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