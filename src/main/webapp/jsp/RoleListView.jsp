<%@page import="in.co.sunrays.proj3.controller.RoleListCtl"%>
<%@page import="in.co.sunrays.proj3.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj3.dto.RoleDTO"%>
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




    <%@include file="Header.jsp"%>

    <center>
        <h1>Role List</h1>
        
        <tr><h2><font color="green">
<%=ServletUtility.getSuccessMessage(request) %>

</font></h2>
</tr>
        
        <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>

        <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
            <table width="80%">
               <tr>
                 <td align="center">
                 <label><b>Name :</b></label>
                  <input type="text" name="name" placeholder="Name" value="<%=ServletUtility.getParameter("name", request)%>"> &nbsp; 
                       
                        <input type="submit" name="operation" value="<%=RoleListCtl.OP_SEARCH %>">
                  	 <input type="submit" name="operation" value="<%=RoleListCtl.OP_RESET %>">
                  	
                  
                   <br><br>
                    </td>
                </tr>
                
            </table>
            <table border="1" width="80%" bordercolor="blue">
                <tr>
                <th align="left"><input type="checkbox" id="UNCHECK" onclick="checkAll(this)" >
                Select</th>
                    <th>S.No.</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Edit</th>
                </tr>
                

                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<RoleDTO> it = list.iterator();
                    while (it.hasNext()) 
                    {
                        RoleDTO dto = it.next();
                %>
                <tr>
                <td>
                <input type="checkbox" name="ids" onclick="checkAl(this)"  value="<%=dto.getId()%>">
                </td>
                    <td><%=index++%></td>
                   
                    <td><%=dto.getName()%></td>
                    <td><%=dto.getDescription()%></td>
                    <td><a href="RoleCtl?id=<%=dto.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="80%">
                <tr>
                    <td>
                    <input type="submit" name="operation" value="<%=RoleListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>>
  </td>
                    <td>
                     <input type="submit" name="operation" value="<%=RoleListCtl.OP_NEW%>">
                    </td>
                    <td>
                    <input type="submit" name="operation" value="<%=RoleListCtl.OP_DELETE%>">
                    </td>
                    <td align="right">
                    <input type="submit" name="operation" value="<%=RoleListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>