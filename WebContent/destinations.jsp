<%@page import="test.AddTourServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="test.User"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="test.Tour"%>
<%@page import="test.WeatherServlet"%>
<%
	boolean hasTours = false;
	boolean hasUser = false;
	User usr = null;
	String firstName = "";
	String role = "";
	ArrayList<Tour> toursList = null;

	if (session.getAttribute("user") == null) {
		hasUser = false;
	} else {
		hasUser = true;
		usr = (User) session.getAttribute("user");
		firstName = usr.getFirstName();
		role = usr.getRole();
	}
	toursList = AddTourServlet.tourDAO.getAllAvailableTours();
	hasTours = !toursList.isEmpty();
%>
<!doctype html>

<html>
<script type="text/javascript">
	function formSubmit(tourId) {
		var fieldTourId = document.getElementById("fieldTourId");
		fieldTourId.value = tourId;

		var reservationForm = document.forms['reservationForm'];
		alert("Резервацията е успешна!");
		reservationForm.submit();
	}
</script>
<head>
<link rel="stylesheet" type="text/css" href="mystyles.css"
	media="screen" />
<title>Title</title>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1251">
<style type="text/css">
.container {
	width: 520px;
	height: autopx;
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
	background: url('images/belogradchik.jpg') no-repeat fixed /*scroll*/;
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
				<li class="active"><a href='destinations.jsp'><span>Дестинации</span></a></li>
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
					<td width="190" height="120" class="active"><table
							class="container2">
							<td><p align="center">
									<img src="images/weather1.png" />
								</p>
								Времето в Бургас е:<br> <%=WeatherServlet.getForecast("Бургас")%></td>
						</table></td>
					<td width="2" rowspan="4"><img src="images/color.png"
						width="2" height="600px" alt="vgreen"></td>

					<td width="596" rowspan="4">
						<div style="overflow-y: scroll; width: 600px; height: 500px"
							align="center">
							

							<%
							if (hasTours) {
						%>
							<form action="ReservationServlet" method="post" class="form"
								id="reservationForm" >
								<input type="hidden" value="" id="fieldTourId"
									name="fieldTourId" />

								<%
									for (int i = 0; i < toursList.size(); i++) {
								%>

								<table class="container" border="0">
									<tr>
										<td align="left" width="150px">Дестинация:</td>
										<td align="right" width = "200px"><%=toursList.get(i).getDestination()%></td>
										<td align="center" rowspan="6">
										<img width = "130px" height="100px" src="imageresource?picture=<%=toursList.get(i).getPicture()%>"/></td>
									</tr>

									<tr>
										<td align="left">Описание:</td>
										<td align="right"><%=toursList.get(i).getDescription()%></td>
									</tr>

									<tr>
										<td align="left">Начална дата:</td>
										<td align="right"><%=toursList.get(i).getStartDate()%></td>
									</tr>

									<tr>
										<td align="left">Крайна дата:</td>
										<td align="right"><%=toursList.get(i).getEndDate()%></td>
									</tr>

									<tr>
										<td align="left">Цена:</td>
										<td align="right"><%=toursList.get(i).getPrice()%></td>
									</tr>

									<tr>
										<td align="left">Оставащи места:</td>
										<td align="right"><%=toursList.get(i).getCapacity()%></td>
									</tr>
									<%
									if(hasUser && usr.getRole().equals("user")){
									%>
									
									
									<tr>
										<td align="center" colspan = "2">
										<input type="button" value="Резервиране"
											name="reserv"
											onclick="formSubmit('<%=toursList.get(i).getTourID()%>')"></td>

									</tr>
									<%
									}else{
										if(hasUser && usr.getRole().equals("admin")){
									%>
									<tr>
										<td align="center" colspan = "2">
										<input type="button" value="Премахване"
											name="reserv"
											onclick="formSubmit('<%=toursList.get(i).getTourID()%>')"></td>

									</tr>
									<%		
										}
									}
									%>

								</table>
								<br>

								<%
									}
								%>
							</form>
							<%
								} else {
							%>
							<table class="container2">
								<tr>
									<td>Няма налични екскурзии.</td>
								</tr>
							</table>
							<%
								}
							%>
						
					</td>
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