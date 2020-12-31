<%@page import="in.co.sunrays.proj3.controller.TimeTableCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.controller.SubjectCtl"%>
<%@page import="java.util.List"%>

<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


</head>
<body>



	<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
       
        <%@ include file="Header.jsp"%>
       
            
        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.TimeTableDTO" scope="request"></jsp:useBean>

        <%
           
        List l = (List) request.getAttribute("courseList");
        List slist = (List) request.getAttribute("subjectList");
        
        %>

        <center>
        	<%
			if (dto.getId() > 0) {
		%>

		<h1>Update TimeTable</h1>
		<%
			} else {
		%>
		<h1>Add TimeTable  </h1>
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



            <input type="hidden" name="id" value="<%=dto.getId()%>">
            <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


            <table align="center" style="margin-left: 40%">
   
                   <tr>
                    <th>Course Name<font color="red">*</font></th>
                    
				                   
				<td>
				
				<%=HTMLUtility.getList("courseId",String.valueOf(dto.getCourseId()), l)%>
				<font color="red">
				 <%=ServletUtility.getErrorMessage("courseId", request)%>
				 </font>
				 </td>
				  
				</tr>
				    
               <tr>
                    <th>Subject Name<font color="red">*</font></th>
                    
				                   
				<td>
				
				<%=HTMLUtility.getList("subjectId",String.valueOf(dto.getSubjectId()), slist)%>
				<font color="red">
				 <%=ServletUtility.getErrorMessage("subjectId", request)%>
				 </font>
				 </td>
				  
				</tr>                
	
			 
				 <tr>
					<th>Semester<font color="red">*</font></th>
					<td>
						<%
							HashMap mapS = new HashMap();
							mapS.put("","----------select-----------");
							mapS.put("1", "1");
							mapS.put("2", "2");
							mapS.put("3", "3");
							mapS.put("4", "4");
							mapS.put("5", "5");
							mapS.put("6", "6");
							mapS.put("7", "7");
							mapS.put("8", "8");


							String htmlList = HTMLUtility.getList("semester", dto.getSemester(), mapS);
						%> <%=htmlList%>
						<font color="red">
						 <%=ServletUtility.getErrorMessage("semester", request)%>
							</font>
					</td>
				</tr>
 
 			
				<tr>
                    <th>Exam Date<font color="red">*</font></th>
                    
                    <td>
                    
                    <input type="text" placeholder="dd/mm/yyyy" size="17" readonly="readonly" name="examDate" id="datepicker1" value="<%=DataUtility.getDateString(dto.getExamDate())%>">
                    
                    	<font color="red"> <%=ServletUtility.getErrorMessage("examDate", request)%></font>
                    	
                    </td>
                </tr> 
                
		 <tr>
					<th>Time<font color="red">*</font></th>
					<td>
						<%
							HashMap mapT = new HashMap();
							mapT.put("","----------select-----------");
							mapT.put( "9am to 12pm","9am to 12pm");
							mapT.put( "12pm to 3pm","12pm to 3pm");
						
							mapT.put( "3pm to 6pm","3pm to 6pm");


							String htmlListt = HTMLUtility.getList("time", dto.getTime(), mapT);
						%> <%=htmlListt%>
						<font color="red">
						 <%=ServletUtility.getErrorMessage("time", request)%>
							</font>
						
					</td>
				</tr>
 
 

       			<tr>
                    <th></th>
                    	<td colspan="2">
                    		<% 
					if (dto.getId() > 0) {
 					%>

						<input type="submit" name="operation" value="<%=TimeTableCtl.OP_UPDATE%>"> 
										
						<input type="submit" name="operation" value="<%=TimeTableCtl.OP_CANCEL%>">
										
					<%
 				} 
					else
					
				{
 					%> 
					
					<input type="submit" name="operation" value="<%=TimeTableCtl.OP_SAVE%>"> 
					<input type="submit"name="operation" value="<%=TimeTableCtl.OP_RESET%>"></td> <%
 	}
 %>

                </tr>
           
            </table>
    
    </form>
    
    </center>
    
    <%@ include file="Footer.jsp"%>

</body>
</html>