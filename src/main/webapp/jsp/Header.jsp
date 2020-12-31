<%@page import="in.co.sunrays.proj3.dto.RoleDTO"%>
<%@page import="in.co.sunrays.proj3.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj3.dto.UserDTO"%>
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%
  
	UserDTO userdto = (UserDTO) session.getAttribute("user");

     boolean userLoggedIn = userdto != null; 

    String welcomeMsg = "Hi, ";

      if (userLoggedIn) 
     
    {
        String role = (String) session.getAttribute("role");
        welcomeMsg += userdto.getFirstName() + " (" + role + ")";
    } else {
        welcomeMsg += "Guest";
    }
 %>    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  
   <script>
 $(function() {
	 
		$("#datepicker").datepicker({
			
			changeMonth : true,
			changeYear : true,
			yearRange : "1980:2000",
			dateFormat: "yy/mm/dd"
			
					});
 
	});
 
 </script>
 <script>
 $(function() {
		// minDate: 4,	 
			$("#datepicker1").datepicker({
				
				beforeShowDay:
					function(dt){
					return[dt.getDay()==0?false:true]
					// 0 is for sunday 1 is for monday ....so on
				},
				changeMonth : true,
				changeYear : true,
				yearRange : "2019:2020",
				dateFormat:"dd/mm/yy",
				minDate:0
				
				//min date for disable previous date....
				
							});

 });
  </script>
 
  
 

 <style>
.head{


 /* background-color:#FFFF99;  */
height:90px;}
</style>
<table width="100%" class="head" style="margin-top: -10px!important;" border="0">
    <tr>
        <td width="90%" ><a href="<%=ORSView.WELCOME_CTL%>"><b><font color="blue">Welcome</b></a> ||
            <%
            if (userdto != null) {
        %> <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>
                <font color="blue">Logout</b></a>

            <%
                } else {
            %> <a href="<%=ORSView.LOGIN_CTL%>"><font color="blue"><b>Login</b></a> <%
     }
 %></td>
        <td rowspan="2">
            <h1 align="right">
                <img src="<%=ORSView.APP_CONTEXT%>/img/image.png" width="90" height="60">
            </h1>
        </td>

    </tr>
    
    <tr>
        <td >
            <h3>
                <%=welcomeMsg%></h3>
        </td>
    </tr>
    

    <%
        if (userLoggedIn) {
    %>

    <tr>
        <td colspan="2" >
        
         <a href="<%=ORSView.MY_PROFILE_CTL%>"><font color="blue">MyProfile</a> ||
         
        <a href="<%=ORSView.GET_MARKSHEET_CTL%>"><font color="blue">Get Marksheet</a> ||
          
            <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><font color="blue">Marksheet Merit List </a> ||
        
        
         
         <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><font color="blue">Change Password </a> ||
         
         	<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"><font color="blue">Time Table List</font></a> ||
        
           
          <%
            if (userdto.getRoleId() == RoleDTO.ADMIN || userdto.getRoleId()==RoleDTO.COLLEGE) 
            {
        %> 
        <a href="<%=ORSView.MARKSHEET_CTL%>"><font color="blue">Add Marksheet</a> ||
        
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><font color="blue">Marksheet List</a> ||
        
            <a href="<%=ORSView.USER_CTL%>"><font color="blue">Add User</b></a> ||
            
            <a href="<%=ORSView.USER_LIST_CTL%>"><font color="blue">User List</b></a> ||
            
            <a href="<%=ORSView.COLLEGE_CTL%>"><font color="blue">Add College</b></a> ||
            
             <a href="<%=ORSView.COLLEGE_LIST_CTL%>"><font color="blue">College List</b></a> ||
             
              <a href="<%=ORSView.STUDENT_CTL%>"><font color="blue">Add Student</b></a> ||
              
               <a href="<%=ORSView.STUDENT_LIST_CTL%>"><font color="blue">Student List</b></a> ||
               
               <a href="<%=ORSView.ROLE_CTL%>"><font color="blue">Add Role</b> </a> || 
              
               <a href="<%=ORSView.ROLE_LIST_CTL%>"><font color="blue">Role List</a> || 
               
               	<a href="<%=ORSView.SUBJECT_CTL%>"><font color="blue">Add Subject</a> ||
	
				<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><font color="blue">Subject List</a> || 
            	
            	<a href="<%=ORSView.COURSE_CTL%>"><font color="blue">Add Course</a> ||
	
				<a href="<%=ORSView.COURSE_LIST_CTL%>"><font color="blue">Course List</a> || 
				
				<a href="<%=ORSView.FACULTY_CTL%>"><font color="blue">Add Faculty</a> || 
	
				<a href="<%=ORSView.FACULTY_LIST_CTL%>"><font color="blue">Faculty List</a> ||
				
				<a href="<%=ORSView.TIMETABLE_CTL%>"><font color="blue">Add Timetable</a> ||
	
			<a href="<%=ORSView.JAVA_DOC_VIEW %>" target="blank"><font color="blue">Java Doc</a> ||
    
               
                <%
  
            }
 %></td>
    
    </tr>
    <%
        }
    %>
</table>

<hr color="blue">