
<%@page import="in.co.sunrays.proj3.controller.TimeTableListCtl"%>
<%@page import="in.co.sunrays.proj3.dto.TimeTableDTO"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>

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







<form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="POST">

  <%@include file="Header.jsp"%>
  
  
    <%
    List l = (List) request.getAttribute("courseList");
    List slist = (List) request.getAttribute("subjectList");
   
    List exam = (List) request.getAttribute("exam");
                %>
    
    <%if(l.size() !=0 && slist.size() !=0){
    %> 
  
    <center>
     
       
     <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.TimeTableDTO" scope="request"></jsp:useBean>  
      <%--   <%
            List l = (List) request.getAttribute("courseList");
        List slist = (List) request.getAttribute("subjectList");
        %>
    
 --%>             <h1>TimeTable List</h1>

<tr><h2><font color="green">
<%=ServletUtility.getSuccessMessage(request) %>

</font></h2>
</tr>
 <tr>
                    <td colspan="8">
                    <font color="red"><h1><%=ServletUtility.getErrorMessage(request)%></h1></font></td>
                </tr>
        
            <table width="80%">
                <tr>
                    <td align="center">
                    
                  <label> <b>Course Name :</b></label> 
                   <%=HTMLUtility.getList("courseId",String.valueOf(dto.getCourseId()), l)%>                  
                    
                    <label> <b>Subject Name :</b></label> 
                  <%=HTMLUtility.getList("subjectId",String.valueOf(dto.getSubjectId()), slist)%>                  
                    
                    
                     <label> <b>Exam Date :</b></label> 
                  <%=HTMLUtility.getList("examDate",String.valueOf(dto.getExamDate()),exam)%>                  
                   
                    <input type="submit" name="operation" value="<%=TimeTableListCtl.OP_SEARCH %>">
                    <input type="submit" name="operation" value="<%=TimeTableListCtl.OP_RESET %>">
                    </td>
                </tr>
            </table>
            <br>
            
            		<% 
			 List list = ServletUtility.getList(request);
			
			if(list.size()==0){}else {%>            
            <table border="1" width="80%" bordercolor="blue">
                <tr>
                    <th align="left">
                    <input type="checkbox" id="UNCHECK" onclick="checkAll(this)"> 
                  Select All</th>
                    <th>S.No.</th>
                     <th>CourseName</th>
                    <th>SubjectName</th>
                    <th>Semester</th>
                    <th>ExamDate</th>
                    <th>Time</th>
                    <th>Edit</th>
                </tr>
               
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                  //  List list = ServletUtility.getList(request);
                    Iterator<TimeTableDTO> it = list.iterator();

                    while (it.hasNext()) {

                         dto = it.next();
                %>
                <tr>
                    <td><input type="checkbox" name="ids" onclick="checkAl(this)" value="<%=dto.getId()%>"></td>
                   
                   <td><%=index++%></td>
   
                   
                  <td><%=dto.getCourseName()%></td> 
                 
                    <td><%=dto.getSubjectName()%></td>
                    <td><%=dto.getSemester()%> semester</td>
                    <td><%=dto.getExamDate()%></td>
                    <td><%=dto.getTime()%></td>
                    <td><a href="TimeTableCtl?id=<%=dto.getId()%>">Edit</a></td>
                </tr>

                <%
                    }
                %>
            </table>
            <table width="80%">
                <tr>
                    <td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    <td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_NEW%>"></td>
                    <td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_DELETE%>"></td>
                    <td align="right"><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
            
              <%
    }}
      %>
        </form>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>