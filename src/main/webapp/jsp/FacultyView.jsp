<%@page import="in.co.sunrays.proj3.controller.UserCtl"%>
<%@page import="in.co.sunrays.proj3.controller.FacultyCtl"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

 
 
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

 <form action="<%=ORSView.FACULTY_CTL%>" method="post">
        <%@ include file="Header.jsp"%>
     
        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.FacultyDTO"
            scope="request"></jsp:useBean>

        <%
            List coursel = (List) request.getAttribute("courseList");
       	 List subl = (List) request.getAttribute("subjectList");
       	 List collegel = (List) request.getAttribute("collegeList");
        
        %>

        <center>
            <%
            if(dto.getId()>0)
            {
            	
           
            
            %>
            
           <h1>Update Faculty</h1> 
            
            <% }
            
            
            else
            { %>
            
            
            <h1>Add Faculty</h1>
           
            <%} %>
           

            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
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
                    
                    	<td>
                    
                    		<input type="text" name="firstName" size="17"  placeholder="First Name"  value="<%=DataUtility.getStringData(dto.getFirstName())%>" onchange="alpha(name)">
                    			<font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
                    	</td>
                </tr>
               
                <tr>
                    <th>Last Name<font color="red">*</font></th>
                    
                    <td>
                    
                    <input type="text" name="lastName" size="17"  placeholder="Last Name" value="<%=DataUtility.getStringData(dto.getLastName())%>">
                    
                    	<font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                
           	   <tr>
                    <th>Qualification<font color="red">*</font></th>
                    <td>
                    	<input type="text" name="qualification" size="17"  placeholder="Qualification" value="<%=DataUtility.getStringData(dto.getQualification())%>">
                    	
                    	<font color="red"> <%=ServletUtility.getErrorMessage("qualification", request)%></font>
                    	
                    </td>
                </tr>
                <tr>
                    <th>Email Id<font color="red">*</font></th>
                    <td>
                    	<input type="text" name="emailId" size="17"  placeholder="Email Id" value="<%=DataUtility.getStringData(dto.getEmailId())%>">
                    	
                    	<font color="red"> <%=ServletUtility.getErrorMessage("emailId", request)%></font>
                    	
                    </td>
                </tr>
                  <tr>
                    <th>Date of  Birth<font color="red">*</font></th>
                    <td>
                    	<input type="text" name="dob" readonly="readonly" size="17"  placeholder="dd/mm/yyyy" id="datepicker" value="<%=DataUtility.getDateString(dto.getDob())%>">
                    	
                    	<font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
                    	
                    </td>
                </tr>
                
                
                  <tr>
                    <th>Mobile No<font color="red">*</font></th>
                    <td>
                    	<input type="text" name="mobNo" maxlength="10" size="17"  placeholder="Mobile Number" value="<%=DataUtility.getStringData(dto.getMobNo())%>">
                    	
                    	<font color="red"> <%=ServletUtility.getErrorMessage("mobNo", request)%></font>
                    	
                    </td>
                </tr>
             
              <tr>
                    <th>Course Name<font color="red">*</font></th>
                  
				<td>
				
				<%=HTMLUtility.getList("courseId",String.valueOf(dto.getCourseId()),coursel)%>
				<font color="red">
				 <%=ServletUtility.getErrorMessage("courseId", request)%>
				 </font>
				 </td>
				  
				</tr>
            
           	   <tr>
                    <th>Subject Name<font color="red">*</font></th>
                   
                    <td>
				
				<%=HTMLUtility.getList("subjectId",String.valueOf(dto.getSubjectId()),subl)%>
				<font color="red">
				 <%=ServletUtility.getErrorMessage("subjectId", request)%>
				 </font>
				 </td>
                </tr>
              	
              	<tr>
                    <th>College Name<font color="red">*</font></th>
                    
                    	<td>
                    
                    	<%= HTMLUtility.getList("collegeId",String.valueOf(dto.getCollegeId()), collegel)%>
                    		
                    		 
                    			<font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font>
                    	</td>
               </tr>
                  <tr>
                 
                <tr>
                    <th></th>
                    
                    	<td colspan="2">
                    		<% 
					if (dto.getId() > 0) {
 					%>

						<input type="submit" name="operation" value="<%=FacultyCtl.OP_UPDATE%>"> 
										
						<input type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>">
										
					<%
 				} 
					else
					
				{
 					%> 
					
					<input type="submit" name="operation" value="<%=FacultyCtl.OP_SAVE%>"> 
					<input type="submit"name="operation" value="<%=FacultyCtl.OP_RESET%>"></td> <%
 	}
 %>
    
                </tr>
           
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>





</body>
</html>