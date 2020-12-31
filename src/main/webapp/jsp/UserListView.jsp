<%@page import="in.co.sunrays.proj3.model.RoleModel"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.controller.UserListCtl"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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


 <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request"></jsp:useBean>     
 
  <form action="<%=ORSView.USER_LIST_CTL%>" method="post">
    <%@include file="Header.jsp"%>

   
    
      
       
       
        <%
            List l = (List) request.getAttribute("roleList");
            List dobl=(List) request.getAttribute("doblist");     
				 System.out.println(dobl);
            %>
  
             
    <%if(l.size() !=0){
    %> 
     <center>
    
    
    
        <h1>User List</h1>
         <tr align="center">  
   <h2> <font color="green">
                    <%=ServletUtility.getSuccessMessage(request)%>
                    </font></h2></tr>
      
          <tr>
               <h1>     <td colspan="8">
                    <font color="red">
                    <%=ServletUtility.getErrorMessage(request)%>
                    </font>
                  
                  </td></h1>
                </tr>
        

      

            <table width="80%">
                <tr>
                    <td align="center">
                    <label>FirstName :</label>
                     <input type="text" name="firstName" placeholder="First name" value="<%=ServletUtility.getParameter("firstName", request)%>"> 
                     &emsp; 
                        <label>LoginId:</label> 
                        <input type="text" name="login" placeholder="Login Id" value="<%=ServletUtility.getParameter("login", request)%>">
                        &emsp;
                        
                       
                        <label> <b>Role :</b></label> 
                   <%=HTMLUtility.getList("roleId",String.valueOf(dto.getRoleId()), l)%>                  
                    
                     <label>Gender:</label> 
                      <%--  <input type="text" name="gender" placeholder="gender" value="<%=ServletUtility.getParameter("gender", request)%>"> --%>
                       <%=HTMLUtility.getList("gender",String.valueOf(dto.getGender()), dobl)%>
                        &emsp;
                       
                    
                    
                   <%--    <label>DOB:</label> 
                        
                            <input type="text" name="dob" placeholder="yyyy/mm/dd" size="17" id="datepicker" value="<%=DataUtility.getDateString(dto.getDob())%>">
                        
						   <%=HTMLUtility.getList("dob",DataUtility.getDateString(dto.getDob()), dobl)%>                      
						   
                        &emsp; 
                     --%>   
                        
                        
                        
                        
                         <input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH %>">
                   
                    <input type="submit" name="operation" value="<%=UserListCtl.OP_RESET %>">
 
                    </td>
                </tr>
            </table>
            <br>
           			<% 
			 List list = ServletUtility.getList(request);
			
			if(list.size()==0){}else {%>
            <table border="2" width="80%" bordercolor="blue" >
                <tr>
     <th align="center"><input type="checkbox" id="UNCHECK" onclick="checkAll(this)"> Select</th>
                    <th >S.No.</th>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>LoginId</th>
                    <th>Mobile No</th>
                    <th>Role</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>Edit</th>
                </tr>

              
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1; 

                   
                    Iterator<UserDTO> it = list.iterator();
                  
                    RoleModel RM=new RoleModel();
                    RoleDTO RB=new RoleDTO();
                   
                    
                    //System.out.println("id is---->"+dto.getId() + "role id ---> "+dto.getRoleId());
                    
                    while (it.hasNext()) 
                    {
                        dto = it.next();
                        RB=RM.findByPK(dto.getRoleId());
                %>
                <tr>
                    <td><input type="checkbox" name="ids" onclick="checkAl(this)"  value="<%=dto.getId()%>"<%=(dto.getRoleId()==1)?"disabled":""%>></td>
                    
                    <td><%=index++%></td>
                    <td><%=dto.getFirstName()%></td>
                    <td><%=dto.getLastName()%></td>
                    <td><%=dto.getLogin()%></td>
                    <td><%=dto.getMobileNo()%></td>
                    <td><%=RB.getName()%></td>
                    <td><%=dto.getGender()%></td>
                    <td><%=dto.getDob()%></td>
                  <td>
                    <%
                    if(dto.getRoleId()!=1)
                    {
                    	%>
                   
                    <a href="UserCtl?id=<%=dto.getId()%>">Edit</a>
                    
                    
                    <% }else{ %>
                    ---
               
             	 <%
                   }
                    }
                %>
                </td>
                 </tr>
         </table>
            <table width="80%">
                <tr>
                    <td >
                    
                    <input type="submit" name="operation" value= "<%=UserListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>>
                    </td>
                    <td >
                     <input type="submit" name="operation" value="<%=UserListCtl.OP_NEW%>">
                     </td>
                     <td >
                     <input type="submit" name="operation" value="<%=UserListCtl.OP_DELETE%>">
                     </td>
                     <td align="right">
                     <input type="submit" name="operation" value="<%=UserListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %> ></td>
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
