
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.dto.StudentDTO"%>
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



 <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">

    <%@include file="Header.jsp"%>
    <center>
    <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.StudentDTO" scope="request"></jsp:useBean>
        <%
            List l = (List) request.getAttribute("collegeList");
        %>
      <%if(l.size() !=0){
    %> 
        <h1>Student List</h1>
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
                    <label> <b>College Name :</b></label> 
                   <%=HTMLUtility.getList("collegeId",String.valueOf(dto.getCollegeId()), l)%>                  
                    
                    <label> <b>FirstName :</b></label> 
                    <input type="text" name="firstName" placeholder="First name" value="<%=ServletUtility.getParameter("firstName", request)%>">
                       <label><b>Email_Id:</b></label>
                         <input type="text" name="email" placeholder="Email Id" value="<%=ServletUtility.getParameter("email", request)%>">
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH %>">
                          <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET %>">
                 </td>
                </tr>
            </table >
            <br>
            			<% 
			 List list = ServletUtility.getList(request);
			
			if(list.size()==0){}else {%>
            
            <table align="center" border="1" width="80%" bordercolor="blue">
                            <tr>
                <th align="left"><input type="checkbox" id="UNCHECK" onclick="checkAll(this)">
                Select</th>
                    <th>S.No.</th>
                    <th>College Name</th>
                    <th>First Name.</th>
                    <th>Last Name.</th>
                    <th>Date Of Birth.</th>
                    <th>Mobile No.</th>
                    <th>Email ID.</th>
                    <th>Edit</th>
                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    //List list = ServletUtility.getList(request);
                    Iterator<StudentDTO> it = list.iterator();

                    while (it.hasNext())
                    {

                         dto = it.next();
                %>
                <tr>
                <td>
                <input type="checkbox" name="ids" onclick="checkAl(this)"  value="<%=dto.getId()%>">
                </td>
                    <td><%=index++%></td>
                   <td><%=dto.getCollegeName()%></td>
                    <td><%=dto.getFirstName()%></td>
                    <td><%=dto.getLastName()%></td>
                    <td><%=dto.getDob()%></td>
                    <td><%=dto.getMobileNo()%></td>
                    <td><%=dto.getEmail()%></td>
                    <td><a href="StudentCtl?id=<%=dto.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="80%">
                <tr>
                    <td>
                    <input type="submit" name="operation" value="<%=StudentListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>>
                    </td>
                    <td>
                    <input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>">
                    </td>
                    
                    <td>
                    <input type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE%>">
                    </td>
                    <td align="right"><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>>
                    </td>
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