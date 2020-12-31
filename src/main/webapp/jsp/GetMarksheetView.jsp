<%@page import="in.co.sunrays.proj3.controller.GetMarksheetCtl"%>
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
<html>
<body>
    <%@ include file="Header.jsp"%>

    <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO" scope="request">
  </jsp:useBean>

    <center >
        <h1>Get Marksheet</h1>

        <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
        </font>

        <H2>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
            </font>
        </H2>

        <form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
       
        
        
<table align="center" style="margin-left: 40%">
            <input type="hidden" name="id" value="<%=dto.getId()%>">
            
                <label>RollNo :</label>&emsp;
                <input type="text" name="rollNo" placeholder="ex-50Bsc01" value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;
                <input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">&emsp;
               
               <br>
               <h3><font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%>
                </font></h3>

                <%
                    if (dto.getName() != null && dto.getName().trim().length() > 0) {
                %>
                </table>
                
                
                 <br>
<table align="center" border="4px"  cellspacing="1" cellpadding="6">
<tr>
			<th colspan="5"  >MARKSHEET</th></tr>
                <tr>
                    <td colspan="2"><b>Rollno :</b>
                <%=DataUtility.getStringData(dto.getRollNo())%></td>
                
                
                    <td colspan="2"><b>Name :</b>
                    <%=DataUtility.getStringData(dto.getName())%></td>
                </tr>
                
                <tr><td><b>Subjects</b></td>
                <td><b>Max Marks</b></td>
                <td><b>Min Marks</b></td>
                <td><b>Marks Obtained</b></td>
                
                </tr>
                
                
                <tr>
                    <td>Physics</td>
                   <td><%=100 %></td><td><%=33 %></td> 
                   <td><%=DataUtility.getStringData(dto.getPhysics())%></td>
                </tr>
                <tr>
                    <td>Chemistry</td>
                    <td><%=100 %></td><td><%=33 %></td>
                    <td><%=DataUtility.getStringData(dto.getChemistry())%></td>
                </tr>
                <tr>
                    <td>Maths</td>
                    <td><%=100 %></td><td><%=33 %></td>
                    <td><%=DataUtility.getStringData(dto.getMaths())%></td>

                </tr>
                
                
                <tr>
                <th><b>Grand Total</b></th>
                <td><%=300  %></td> <td></td> 
							<td align="center">
								<%
									int marksTotal = (DataUtility.getIntegerData(dto.getPhysics())
												+ DataUtility.getIntegerData(dto.getChemistry())
												+ DataUtility.getIntegerData(dto.getMaths()));
								%><%=marksTotal%></td>
               
                
                </tr>
                
                
                <tr>
                    <th colspan="2" align="left"><b>Result:</b></th>
                                        <td colspan="5">   <%
									String physics = ((100 >= DataUtility.getIntegerData(dto.getPhysics()))
												&& (DataUtility.getIntegerData(dto.getPhysics()) >= 35)) ? "Pass" : "Fail";
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
                </tr>
                
                
                  <tr>
                    <th colspan="2" align="left"><b>Percentage:</b></th>
                    <td colspan="5"><%=marksTotal/3%>%</td>
                </tr>
                
              
                </table>
            
            <%
                }
            %>
        </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>