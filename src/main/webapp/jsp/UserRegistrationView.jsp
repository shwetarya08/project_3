<%@page import="in.co.sunrays.proj3.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<html>
<body>
	<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request">
		</jsp:useBean>

		<center>
			<h1>User Registration</h1>

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<%-- <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
 --%>			</H2>

			<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=dto.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


			<table align="center" style="margin-left: 40%">

				<tr>
					<th>First Name<font color="red">*</font></th>
					<td><input type="text" name="firstName" placeholder="First Name"
						value="<%=DataUtility.getStringData(dto.getFirstName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th>Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName" placeholder="Last Name"
						value="<%=DataUtility.getStringData(dto.getLastName())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%>
					</font></td>
				</tr>
				<tr>
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login"
						placeholder="Must be Email ID"
						value="<%=DataUtility.getStringData(dto.getLogin())%>"> <font
						color="red"><%=ServletUtility.getErrorMessage("login", request)%>
					</font></td>
				</tr>
				<tr>
					<th>Password<font color="red">*</font></th>
					<td><input type="password" name="password" placeholder="Password"
						value="<%=DataUtility.getStringData(dto.getPassword())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<th>Confirm Password<font color="red">*</font></th>
					<td><input type="password" name="confirmPassword" placeholder="confirm password"
						value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%>
					</font>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
					
					</td>
				</tr>
				<tr>
					<th>Gender<font color="red">*</font></th>
					<td>
						<%
							HashMap map = new HashMap();
						map.put("","-------------Select------------");	
						map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
						%> <%=htmlList%>
<font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%>
					</font>
					</td>
				</tr>

				<tr>
					<th>Date Of Birth<font color="red">*</font></th>
					<td><input type="text" name="dob" placeholder="dd/mm/yyyy" id="datepicker"
						value="<%=DataUtility.getDateString(dto.getDob())%>"> 
						 <font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				
				
				<tr>
					<th>MobileNo<font color="red">*</font></th>
					<td>
					<input type="text" name="mob" placeholder="mobileNo" 	value="<%=DataUtility.getStringData(dto.getMobileNo())%>"> 
						 <font color="red"> 
						 <%=ServletUtility.getErrorMessage("mob", request)%></font></td>
				</tr>
				
				<tr>
					<th></th>
					<td colspan="2">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=UserRegistrationCtl.OP_SIGN_UP%>">
					</td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>
