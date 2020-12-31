<%@page import="in.co.sunrays.proj3.controller.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<html>
<body>
	<form action="<%=ORSView.USER_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
			scope="request" />





		<%
			List l = (List) request.getAttribute("roleList");
		%>



		<center>

			<%
			if (dto.getId() > 0) {
		%>

			<h1>Update User</h1>
			<%
			} else {
		%>
			<h1>Add User</h1>
			<%
			}
		%>


			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
<h2>


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
					<td><input type="text" name="firstName" size="17"
						placeholder=" First Name"
						value="<%=DataUtility.getStringData(dto.getFirstName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>

				<tr>
					<th>Last Name<font color="red">*</font></th>
					<td><input type="text" name="lastName" size="17"
						placeholder="Last Name"
						value="<%=DataUtility.getStringData(dto.getLastName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th>LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" placeholder="Login Id"
						size="17" value="<%=DataUtility.getStringData(dto.getLogin())%>"
						<%=(dto.getId() > 0) ? "readonly" : ""%>> <font
						color="red"> <%=ServletUtility.getErrorMessage("login", request)%>
					</font></td>
				</tr>

				<tr>
					<th>Mobile No<font color="red">*</font></th>
					<td><input type="text" name="mobNo" maxlength="10" placeholder="Mobile No"
						size="17"
						value="<%=DataUtility.getStringData(dto.getMobileNo())%>"
						<%=(dto.getId() > 0) ? "readonly" : ""%>> <font
						color="red"> <%=ServletUtility.getErrorMessage("mobNo", request)%>
					</font></td>
				</tr>




				<tr>
					<th>Password<font color="red">*</font></th>
					<td><input type="password" name="password"
						placeholder="Password" size="17"
						value="<%=DataUtility.getStringData(dto.getPassword())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>

				<tr>
					<th>Confirm Password<font color="red">*</font></th>
					<td><input type="password" name="confirmPassword"
						placeholder="Confirm Password" size="17"
						value="<%=DataUtility.getStringData(dto.getPassword())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%>
					</font></td>
				</tr>

				<tr>
					<th>Gender<font color="red">*</font></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("","-----------select----------");
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
						%> <%=htmlList%> <font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%>
					</font>

					</td>
				</tr>

				<tr>
					<th>Role Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("roleId",String.valueOf(dto.getRoleId()), l)%>
						<font color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%>
					</font></td>
				</tr>


				<tr>
					<th>Date Of Birth<font color="red">*</font></th>
					<td>
					
					<input type="text" name="dob" placeholder="yyyy/mm/dd" size="17" id="datepicker" value="<%=DataUtility.getDateString(dto.getDob())%>"> 
					
					</a><font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%>
					</font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2">
						<% 
					if (dto.getId() > 0)
					{
 					%> 
 					<input type="submit" name="operation"value="<%=UserCtl.OP_UPDATE%>">
 					 <input type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>"> 
 					 <%
 				} 
					else
					
				{
 					%> <input type="submit" name="operation" value="<%=UserCtl.OP_SAVE%>"> 
 					<input type="submit" name="operation" value="<%=UserCtl.OP_RESET%>">
					</td>
					<%
 	}
 %>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>