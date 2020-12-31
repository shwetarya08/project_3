<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.controller.SubjectCtl"%>
<%@page import="in.co.sunrays.proj3.dto.SubjectDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj3.controller.SubjectListCtl"%>
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



<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">
<%@include file="Header.jsp"%>


  <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.SubjectDTO"
            scope="request"></jsp:useBean>
    
    

        <%
            List coursel = (List) request.getAttribute("courseList");
                   
        %>
    
 <%if(coursel.size() !=0){
    %>
    <center>
        <h1>Subject List</h1>

<tr><h2><font color="green">
<%=ServletUtility.getSuccessMessage(request) %>

</font></h2>
</tr>

 				<tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>
        
            <table width="80%">
                <tr>
                    <td align="center">
                    
                      <label> <b>Course Name :</b></label> 
                   <%=HTMLUtility.getList("courseId",String.valueOf(dto.getCourseId()), coursel)%>                  
                 
                    
                    
                    <label><b>Subject Name :</b></label> 
                    <input type="text"  name="name" placeholder="subject Name" value="<%=ServletUtility.getParameter("name", request)%>">
                        &nbsp; <input type="submit" name="operation" value="<%=SubjectListCtl.OP_SEARCH %>">
                        <input type="submit" name="operation" value="<%=SubjectListCtl.OP_RESET %>">
                    </td>
                </tr>
                
            </table>
            
            <br>
           	         <% 
			 List list = ServletUtility.getList(request);
			
			if(list.size()==0){}
			else {
			%>
            
            
           	            <table border="1" width="80%" bordercolor="blue">
                <tr>
                	<th align="left"><input type="checkbox" id="UNCHECK" onclick="checkAll(this)">
                	Select</th>
                    <th>S.No.</th>
                   
                    <th>Name</th>
                    <th>Description</th>
                     
                    <th>CourseName</th>
                    <th>Edit</th>
                   
                </tr>
               

                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                   // List list = ServletUtility.getList(request);
                    Iterator<SubjectDTO> it = list.iterator();
                    while (it.hasNext()) {
                       dto = it.next();
                %>
                <tr>
                	<td><input type="checkbox" name="ids" onclick="checkAl(this)"  value="<%=dto.getId()%>"></td>
                    <td><%=index++%></td>
                  
                    <td><%=dto.getName()%></td>
                    <td><%=dto.getDescription()%></td>
                   
                    <td><%=dto.getCourseName()%></td>
                    <td><a href="SubjectCtl?id=<%=dto.getId()%>">Edit</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="80%">
                <tr>
                    <td>
                    <input type="submit" name="operation" value="<%=SubjectListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    <td>
                    <input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEW%>">
                    </td>
                    
                    <td>
                    <input type="submit" name="operation" value="<%=SubjectListCtl.OP_DELETE%>">
                    </td>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=SubjectCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
      
      <%
    }}
      %>
      
        </form>
    </center>
  
   <%@ include file="Footer.jsp"%>



</body>
</html>