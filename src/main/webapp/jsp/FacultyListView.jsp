<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.controller.FacultyListCtl"%>
<%@page import="in.co.sunrays.proj3.dto.FacultyDTO"%>
<%@page import="java.util.Iterator"%>
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

<script type="text/javascript">

function checkAll(bx)
{
	var form=bx.form;
	var isChecked= bx.checked;
	for(var i=0;i<form.length;i++)
		{
		if(form[i].type=='checkbox')
			{
			form[i].checked=isChecked;
			}
		}
	
	}
function checkAl(bx)
{
	document.getElementById("UNCHECK").checked=false;
	}


</script>



 <form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">

  <%@include file="Header.jsp"%>
    
      <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.FacultyDTO"
            scope="request"></jsp:useBean>
    
    

        <%
            List coursel = (List) request.getAttribute("courseList");
              	 List collegel = (List) request.getAttribute("collegeList");
        
        %>
            
    
    <%if(coursel.size() !=0 && collegel.size() !=0){
    %> 
  
    
    
    <center>
        <h1>Faculty List</h1>

<tr align="center">  
   <h2> <font color="green">
                    <%=ServletUtility.getSuccessMessage(request)%>
                    </font></h2></tr>
      
       <tr>
                    <td colspan="8"><font color="red"><h1><%=ServletUtility.getErrorMessage(request)%></h1></font></td>
                </tr>
            <table width="90%">
                <tr>
                <td align="center">
                 <label> <b>Course Name :</b></label> 
                   <%=HTMLUtility.getList("courseId",String.valueOf(dto.getCourseId()), coursel)%>                  
                 
                
                    
                    <label> <b>FirstName :</b></label> 
                    <input type="text" name="firstName" placeholder="FirstName" value="<%=ServletUtility.getParameter("firstName", request)%>">
                      
                       <label> <b>College Name :</b></label> 
                   <%=HTMLUtility.getList("collegeId",String.valueOf(dto.getCollegeId()),collegel)%>                  
                        <label><b>Email_Id :</b></label>
                        
                        <input type="text" name="emailId" placeholder="Email Id" value="<%=ServletUtility.getParameter("emailId", request)%>">
                        
                        <input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH %>">
            <input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET %>">
            
            
                </td>
                </tr>
            </table>
            <br>
            
            	<% 
			 List list = ServletUtility.getList(request);
			
			if(list.size()==0){}else {%>
            <table border="1" width="90%" bordercolor="blue">
                <tr>
                <th align="center"><input type="checkbox" id="UNCHECK" onclick="checkAll(this)">
                Select</th>
                    <th>S.No.</th>
                    <!-- <th>ID.</th>
                    <th>College Id.</th> -->
                    <th>Course Name</th>
                    
                    <th>First Name.</th>
                    <th>Last Name.</th>
                 <!--  <th>Subject ID</th> -->
                  <th>Subject Name</th>
                    <th>College Name</th>
                    <th>Qualification</th>
                    <th>Email ID.</th>
                    <th>Dob.</th>
                    <th>Mob No.</th>
                   
                    <th>Edit</th>
                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                   // List list = ServletUtility.getList(request);
                    Iterator<FacultyDTO> it = list.iterator();

                    while (it.hasNext()) {

                        dto = it.next();
                %>
                <tr>
             <td>   <input type="checkbox"  name="ids" onclick="checkAl(this)"  value="<%=dto.getId()%>"></td>
                    <td><%=index++%></td>
                     <%-- <td><%=dto.getId()%></td>
 --%>                     
                      <td><%=dto.getCourseName()%></td> 
                    
                    <td><%=dto.getFirstName()%></td>
                    <td><%=dto.getLastName()%></td>
                    <td><%=dto.getSubjectName()%></td> 
                    <td><%=dto.getCollegeName()%></td>
                    <%-- <td><%=dto.getCollegeName()%></td> --%>
                    <td><%=dto.getQualification()%></td>
                    <td><%=dto.getEmailId()%></td>
                    <td><%=dto.getDob()%></td>
                    <td><%=dto.getMobNo()%></td>
                   
                    <td><a href="FacultyCtl?id=<%=dto.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="90%">
                <tr>
                    <td>
                    <input type="submit" name="operation" value="<%=FacultyListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    
                    <td> <input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>
                    <td> <input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
                    <td align="right">
                    <input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
         <%
    }}
      %>

        </form>
    </center>
 <%@ include file="Footer.jsp"%>
</body>
</html>