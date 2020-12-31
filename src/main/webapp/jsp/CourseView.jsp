<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.controller.CourseCtl"%>
<%@page import="in.co.sunrays.proj3.controller.CourseListCtl"%>
<%@page import="in.co.sunrays.proj3.dto.CourseDTO"%>
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
<body>
<form action="<%=ORSView.COURSE_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

      <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.CourseDTO" scope="request"></jsp:useBean>
        <center>
        
        	<%
			if (dto.getId() > 0) {
		%>

		<h1>Update Course</h1>
		<%
			} else {
		%>
		<h1>Add Course</h1>
		<%
			}
		%>
  
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
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
                    <input type="text" name="name" placeholder="Name" size="18"   value="<%=DataUtility.getStringData(dto.getName())%>">
                        <font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
                        </font>
                        </td>
                </tr>
            
                <tr>
                    <th>Description<font color="red">*</font></th>
                    <td>
                    <textarea name="description" placeholder="Describe here.."   rows="4" cols="19"  >
                    	<%=DataUtility.getStringData(dto.getDescription())%> 
                      </textarea>
                     
                        <font color="red"> <%=ServletUtility.getErrorMessage("description", request)%>
                        </font>
                        </td>
                </tr>
              	<tr>
					<th>Duration<font color="red">*</font> </th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("","-----------select-----------");
							map.put("1", "1 year");
							map.put("2", "2 year");
							map.put("3", "3 year");
							map.put("4", "4 year");


							
							
							String htmlList = HTMLUtility.getList("duration", dto.getDuration(), map);
						%> <%=htmlList%>
						<font color="red">
						 <%=ServletUtility.getErrorMessage("duration", request)%>
							</font>
						
					</td>
				</tr>				
 
 			

                <tr>
                    <th></th>
                    <td colspan="2">
                  	<% 
					if (dto.getId() > 0) {
 					%>

						<input type="submit" name="operation" value="<%=CourseCtl.OP_UPDATE%>"> 
										
						<input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>">
										
					<%
 				} 
					else
					
				{
 					%> 
					
					<input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>"> 
					<input type="submit"name="operation" value="<%=CourseCtl.OP_RESET%>"></td> <%
 	}
 %>

                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>

</body>
</html>