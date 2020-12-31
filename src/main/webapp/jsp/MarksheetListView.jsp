<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.controller.MarksheetListCtl"%>
<%@page import="in.co.sunrays.proj3.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj3.dto.MarksheetDTO"%>
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



 <form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">

    <%@include file="Header.jsp"%>
    
    
        <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO" scope="request">
        </jsp:useBean>  
        <%
            List marksheetl = (List) request.getAttribute("marksheetList");
        
        %>
    
    <center>
    
        <h1>Marksheet List</h1>
        
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
                    		<label><b>RollNo :</b></label> 
                    		<% System.out.println(dto.getRollNo()); %>
						   <%=HTMLUtility.getList("rollNo",String.valueOf(dto.getId()), marksheetl)%>                      
                        <label> <b>Student Name :</b></label> 
                                 
                    <input type="text" name="name" placeholder="Student name" value="<%=ServletUtility.getParameter("name", request)%>">&emsp;
   
                        
                        
                        <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_SEARCH %>">
               <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_RESET %>"></td>
              
                </tr>
            </table>
            <br>
            <table border="1" width="80%" bordercolor="blue">
                <tr>
                    <th align="left"><input type="checkbox" id="UNCHECK" onclick="checkAll(this)">
                    Select</th>
                    <th>SNo</th>
                   
                    <th>RollNo</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
                    <th>Total</th>
                    <th>Result</th>
                    <th>Percentage(%)</th>
                    <th>Edit</th>
                </tr>
                              <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetDTO> it = list.iterator();

                    while (it.hasNext())
                    {

                        dto = it.next();
                %>
                <tr>
                    <td>
                    <input type="checkbox" name="ids" onclick="checkAl(this)"  value="<%=dto.getId()%>">
                    </td>
                    <td><%=index++%></td>
                 
                     <td><%=dto.getRollNo()%></td>
                    <td><%=dto.getName()%></td>
                    <td><%=dto.getPhysics()%></td>
                    <td><%=dto.getChemistry()%></td>
                    <td><%=dto.getMaths()%></td>
                    
                    <td><% 
                    int marksTotal=(DataUtility.getIntegerData(dto.getPhysics())+
                    		DataUtility.getIntegerData(dto.getChemistry())+
                    				DataUtility.getIntegerData(dto.getMaths()));
                                        
                    %>
                    <%=marksTotal %></td>
                    
                <td>  
                  <%
									String physics = ((100 >= DataUtility.getIntegerData(dto.getPhysics()))
												&& (DataUtility.getIntegerData(dto.getPhysics()) >= 33)) ? "Pass" : "Fail";
								%>
								
								<%
									String chemistry = ((100 >= DataUtility.getIntegerData(dto.getChemistry()))
												&& (DataUtility.getIntegerData(dto.getChemistry()) >= 35)) ? "Pass" : "Fail";
								%>
								<%
									String maths = ((100 >= DataUtility.getIntegerData(dto.getMaths()))
												&& (DataUtility.getIntegerData(dto.getMaths()) >= 35)) ? "Pass" : "Fail";
								%>
								
								<%
									String result = ((physics.equals("Pass")) && (chemistry.equals("Pass")) && (maths.equals("Pass")))
												? "Pass" : "Fail";
								%><%=result%>
	
								
								
								</td>
                    
                    
                    <td><%=marksTotal/3 %>%</td>
                    
                    <td><a href="MarksheetCtl?id=<%=dto.getId()%>">Edit</a></td>
                </tr>

                <%
                    }
                %>
            </table>
            <table width="80%">
                <tr>
                    <td>
                    <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>>
                    </td>
                    <td>
                    <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_NEW%>">
                    </td>
                    <td>
                    <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_DELETE%>">
                    
                    </td>
                    <td align="right">
                    <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>">
             <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </form><br>
        <br>
        <br>
        
    </center>
   <%@include file="Footer.jsp"%> 
</body>
</html>
