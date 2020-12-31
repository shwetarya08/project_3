<%@page import="in.co.sunrays.proj3.controller.CollegeCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<html>
<body>
    <form action="CollegeCtl" method="POST">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.CollegeDTO" scope="request">
            </jsp:useBean>

        <center>
            	<%
			if (dto.getId() > 0) {
		%>

		<h1>Update College</h1>
		<%
			} else {
		%>
		<h1>Add College</h1>
		<%
			}
		%>
   
               <H2>
                <font color="green">
                	 <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> 
                <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <input type="hidden" name="id" value="<%=dto.getId()%>">
             <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
             <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
                 <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

            <table align="center" style="margin-left: 40%">
   
                <tr>
                    <th>Name<font color="red">*</font></th>
                    <td>
                    <input type="text" name="name" placeholder="Enter Name" value="<%=DataUtility.getStringData(dto.getName())%>">
                    <font color="red">
                     <%=ServletUtility.getErrorMessage("name", request)%>
                     </font>
                     </td>
                </tr>
                
                <tr>
                    <th>Address<font color="red">*</font></th>
                    <td>
                    <input type="text" name="address" placeholder="Enter Address" value="<%=DataUtility.getStringData(dto.getAddress())%>">
                    <font color="red"> 
                    <%=ServletUtility.getErrorMessage("address", request)%>
                    </font>
                    </td>
                </tr>
                <tr>
                    <th>State<font color="red">*</font></th>
                    <td>
                    <input type="text" name="state" placeholder="Enter State" value="<%=DataUtility.getStringData(dto.getState())%>">
                    <font color="red"> 
                    <%=ServletUtility.getErrorMessage("state", request)%></font></td>
                </tr>
                <tr>
                    <th>City<font color="red">*</font></th>
                    <td>
                    <input type="text" name="city" placeholder="Enter City" value="<%=DataUtility.getStringData(dto.getCity())%>">
       <font color="red">
        <%=ServletUtility.getErrorMessage("city", request)%>
        </font>
        </td>
             
                </tr>
                  <br>
                <br>
                <tr>
                    <th>PhoneNo<font color="red">*</font></th>
                    <td>
                    <input type="text" name="phoneNo" placeholder="Enter PhoneNo" value="<%=DataUtility.getStringData(dto.getPhoneNo())%>">
 			<font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%>
 			</font>
 			</td>
                </tr>


                <tr>
                    <th></th>
                    <td colspan="2">
                  	<% 
					if (dto.getId() > 0) {
 					%>

						<input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>"> 
										
						<input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>">
										
					<%
 				} 
					else
					
				{
 					%> 
					
					<input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>"> 
					<input type="submit"name="operation" value="<%=CollegeCtl.OP_RESET%>"></td> <%
 	}
 %>
          </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>
