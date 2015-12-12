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
	width: 190px;
	height: 150px;
	clear: both;
	border-radius: 10px 10px 10px 10px;
}

.container2 {
	width: 580px;
	height: 400px;
	clear: both;
	border-radius: 10px 10px 10px 10px;
}

body {
	background: url('images/mauntain2.jpg') no-repeat fixed /*scroll*/;
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
				<li class="active"><a href='forus.jsp'><span>За нас</span></a></li>
				<li><a href='contacts.jsp'><span>Контакти</span></a></li>
				<%
					if (role.equals("admin")) {
				%><li ><a href='reg.jsp'>Публикуване</a></li>
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
							<td><p align="center">
									<img src="images/weather1.png" />
								</p>
								Времето в Боровец е:<br> <%=WeatherServlet.getForecast("Боровец")%></td>
						</table></td>
					<td width="2" rowspan="4"><img src="images/color.png"
						width="2" height="600px" alt="vgreen"></td>

					<td width="596" rowspan="4" align="center">

						<table class="container2">
							<tr>
								<td align="left">CSTours ООД е българска туроператорска
									фирма, създадена съгласно търговското законодателство и
									регистрирана от Агенцията по туризъм, с разрешение за
									извършване и предлагане на комплексни туристически услуги на
									територията на България :</td>

							</tr>
							<tr>
								<td align="left"><p>• туристическа информация</p>
									<p>• преференциални цени за хотелски резервации</p>
									<p>• настаняване във вили, къщи, квартири, манастири, и др.</p>
									<p>• безплатни резервации в ресторанти</p>
									<p>• уикенд и празнични почивки</p>
									<p>• почивки на море, планина и др.</p>
									<p>• специализирани и бизнес пътувания, конгреси, изложения
										и др.</p></td>

							</tr>
							<tr>
								<td align="left">Стрес, умора и липса на свободно време...
									Все по-често си задавате въпроса какво би Ви доставило
									удоволствие и би Ви накарало да се почувствате щастливи и
									отпочинали? Екипът на CSTours знае отговора! Използвайки опита
									и ноу хау събрани от нас се стремим да работим единствено в
									полза на нашите клиенти. За нас всеки клиент е специален и
									трябва да бъде максимално удовлетворен.</td>

							</tr>
							<tr>
								<td align="left">Позволете ни да спестим от времето Ви и
									изпълним всички Ваши желания!!!</td>

							</tr>

						</table>
					</td>
				</tr>
				<tr>
					<td height="120" class="active"><table class="container"
							align="center">
							<td align="center">Посетете страницата ни във фейсбук <br>
								<a
								href="https://www.facebook.com/profile.php?id=100006379088269"><img
								src="images/facebook.jpg" width="150" height="80" /></a></td>
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