

<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.controller.StudentCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.controller.StudentListCtl"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

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
    <form action="<%=ORSView.STUDENT_CTL%>" method="post">
        	<%@ include file="Header.jsp"%>
    
  

        
        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.StudentDTO" scope="request"></jsp:useBean>
        <%
            List l = (List) request.getAttribute("collegeList");
        %>

        <center>
        
        
        	<%
			if (dto.getId() > 0) {
		%>

		<h1>Update Student</h1>
		<%
			} else {
		%>
		<h1>Add Student</h1>
		<%
			}
		%>
   

            <H2>
                <font color="red"> 
                <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>



            <input type="hidden" name="id" value="<%=dto.getId()%>">
            <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


            <table align="center" style="margin-left: 40%">
   
        
                     
                             <tr>
                    <th>First Name<font color="red">*</font></th>
                    <td><input type="text" name="firstName" size="17"  placeholder="Enter First Name" value="<%=DataUtility.getStringData(dto.getFirstName())%>">
                    <font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
                    </td>
                </tr>
                
                <tr>
                    <th>Last Name<font color="red">*</font></th>
                    <td><input type="text" name="lastName" size="17"  placeholder="Enter Last Name" value="<%=DataUtility.getStringData(dto.getLastName())%>"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                
                 <tr>
                    <th>Mobile Number<font color="red">*</font></th>
                    <td><input type="text" name="mobileNo" maxlength="10" size="17"  placeholder="Enter Mobile Number" value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
  <font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>
                
                <tr>
                    <th>Email Id<font color="red">*</font></th>
                    <td>
                    <input type="text" name="email" placeholder="Enter Email Id" size="17"  value="<%=DataUtility.getStringData(dto.getEmail())%>">
                    <font color="red">
                     <%=ServletUtility.getErrorMessage("email",request)%>
                     </font>
                     </td>
                </tr>
                
                    <tr><th>College Name<font color="red">*</font></th>
<td>

<%=HTMLUtility.getList("collegeId",String.valueOf(dto.getCollegeId()), l)%>
  <font color="red">
 
 <%=ServletUtility.getErrorMessage("collegeId",request)%>
                     </font>
</td>

</tr>
         
           
                
                
                 <tr>
                    <th>Date Of Birth<font color="red">*</font></th>
                    <td>
                    <input type="text"  name="dob" size="17"  placeholder="yyyy/mm/dd"
                     id="datepicker"  value="<%=DataUtility.getDateString(dto.getDob())%>">
                        <font color="red">
                         <%=ServletUtility.getErrorMessage("dob", request)%>
                         </font>
                        </td>
                </tr>  
              
               
                   
                <tr>
                    <th></th>
                    <td colspan="2">
                   	<% 
					if (dto.getId() > 0) {
 					%>

						<input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE%>"> 
										
						<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">
										
					<%
 				} 
					else
					
				{
 					%> 
					
					<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>"> 
					<input type="submit"name="operation" value="<%=StudentCtl.OP_RESET%>"></td> <%
 	}
 %>
         </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</html>