<%@page import="in.co.sunrays.proj3.util.DataValidator"%>
<%@page import="in.co.sunrays.proj3.controller.MarksheetCtl"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO"
			scope="request"></jsp:useBean>
		<%
			List l = (List) request.getAttribute("studentList");
		%>

		<%
			if (dto.getId() > 0) {
		%>

		<h1 align="center">Update Marksheet</h1>
		<%
			} else {
		%>

		<h1 align="center">Add Marksheet</h1>
		<%
			}
		%>

		</head>
		<body>
			<div id="container">
				<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">

					<div id="subcontent">
						<h1 align="center">
							<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>

							<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
						</h1>

						<input type="hidden" name="id" value="<%=dto.getId()%>">
						<input type="hidden" name="createdBy"
							value="<%=dto.getCreatedBy()%>"> <input type="hidden"
							name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
							type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<center>
							<table align="center" style="margin-left: 40%">

								<%-- 	<tr>
									<td colspan="2" height="30">
										<h1 align="center">
											<%
												if (dto.getId() > 0) {
											%>

											Update Marksheet
											<%
												} else {
											%>
											Add Marksheet
											<%
												}
											%>

										</h1>
									</td>

								</tr>
								<tr height="10"></tr>
 --%>
								<tr>
									<th align="left">RollNo<font color="red">*</font></th>
									<td><input type="text" name="rollNo"
										placeholder="Enter Roll Number" size="17"
										value="<%=DataUtility.getStringData(dto.getRollNo())%>"
										<%=(dto.getId() > 0) ? "readonly" : ""%>> <font
										color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
									</td>
								</tr>

								<tr>
									<th align="left">Student Name<font color="red">*</font></th>
									<td><%=HTMLUtility.getList("studentId", String.valueOf(dto.getStudentId()), l)%>

										<font color="red"><%=ServletUtility.getErrorMessage("studentId", request)%></font>
									</td>
								</tr>
								<%
									String phy;
									if (dto.getPhysics() > 0) {
										phy = DataUtility.getStringData(dto.getPhysics());
									}

									else {
										phy = DataUtility.getStringData(dto.getPhysicsS());
									}
								%>


								<tr>
									<th align="left">Physics<font color="red">*</font></th>
									<td><input type="text" name="physics"
										placeholder="physics" size="17" value="<%=phy%>"> <font
										color="red"> <%=ServletUtility.getErrorMessage("physics", request)%>
									</font></td>
								</tr>

								<%
									String chem;
									if (dto.getChemistry() > 0) {
										chem = DataUtility.getStringData(dto.getChemistry());
									}

									else {
										chem = DataUtility.getStringData(dto.getChemistryS());
									}
								%>


								<tr>
									<th align="left">Chemistry<font color="red">*</font></th>
									<td><input type="text" name="chemistry"
										placeholder="Chemistry" size="17"
										value="<%=chem%>"> <font
										color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%>
									</font></td>
								</tr>
								<%
									String maths;
									if (dto.getMaths() > 0) {
										maths = DataUtility.getStringData(dto.getMaths());
									}

									else {
										maths = DataUtility.getStringData(dto.getMathsS());
									}
								%>
								<tr>
									<th align="left">Maths<font color="red">*</font></th>
									<td><input type="text" name="maths" placeholder="Maths"
										size="17" value="<%=maths%>"> <font color="red">
											<%=ServletUtility.getErrorMessage("maths", request)%>
									</font></td>
								</tr>

								<tr>
									<th></th>
									<td colspan="2">
										<%
											if (dto.getId() > 0) {
										%> <input type="submit" name="operation"
										value="<%=MarksheetCtl.OP_UPDATE%>"> <input
										type="submit" name="operation"
										value="<%=MarksheetCtl.OP_CANCEL%>"> <%
 	} else

 	{
 %> <input type="submit" name="operation"
										value="<%=MarksheetCtl.OP_SAVE%>"> <input
										type="submit" name="operation"
										value="<%=MarksheetCtl.OP_RESET%>">
									</td>
									<%
										}
									%>
									</td>
								</tr>
							</table>
						</center>
					</div>
				</form>

				<%@ include file="Footer.jsp"%>
		</body>
</html>