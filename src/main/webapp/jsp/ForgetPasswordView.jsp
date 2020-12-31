<%@page import="in.co.sunrays.proj3.controller.ForgetPasswordCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<html>
<body>
    <form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

        <%@ include file="Header.jsp"%>

      		<%--  <jsp:usedto id="dto" class="in.co.sunrays.proj3.dto.Userdto"
         	scope="request"></jsp:usedto>
 --%>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request"></jsp:useBean>
        <center>
            <h1>Forgot your password?</h1>
            <input type="hidden" name="id" value="<%=dto.getId()%>">

            <table>
                 <lable>Submit your email address and we'll send you password.</lable><br><br>
                
                    <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
                <label>Email Id :</label>&emsp;
                
                <input type="text" name="login" placeholder="Enter ID Here" value="<%=ServletUtility.getParameter("login", request)%>">&emsp;
               
              <input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"><br>
               
            
            </table>
            
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>