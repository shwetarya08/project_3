package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CollegeModel;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.model.FacultyModel;
import in.co.sunrays.proj3.model.SubjectModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name="FacultyListCtl",urlPatterns={"/ctl/FacultyListCtl"})
public class FacultyListCtl extends BaseCtl {

	 @Override
	    protected void preload(HttpServletRequest request) {
	       
	    	  CourseModel cmodel = new CourseModel();
		        try {
		            List coursel = cmodel.list();
		            request.setAttribute("courseList",coursel);
		        } catch (ApplicationException e) {
		            log.error(e);
		        }

		      	    	
	    	
	    	CollegeModel model = new CollegeModel();
	        try {
	            List collegel = model.list();
	            request.setAttribute("collegeList", collegel);
	        } catch (ApplicationException e) {
	            log.error(e);
	        }

	    }
	 
	 

	
	
	
	
	private static Logger log = Logger.getLogger(FacultyListCtl.class);

	 protected BaseDTO populatedto(HttpServletRequest request){
			
			FacultyDTO dto=new FacultyDTO();
			dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
			
			
			dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
			dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
			dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
			dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
			//dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
			//dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
			//dto.setCollegeName(DataUtility.getString(request.getParameter("collegeName")));
			dto.setQualification(DataUtility.getString(request.getParameter("qualification")));
			dto.setEmailId(DataUtility.getString(request.getParameter("emailId")));
			dto.setDob(DataUtility.getDate(request.getParameter("dob")));
			dto.setMobNo(DataUtility.getString(request.getParameter("mobNo")));
			dto.setId(DataUtility.getLong(request.getParameter("id")));
			
	
			return dto;
	
	
	
	 }
	 
	 
	  /**
	     * Contains display logic
	     */

	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	 {
		  
			log.debug("FacultyListCtl doGet Start");
		        
			  List list =new ArrayList();
			   int pageNo = 1;
		        
		        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		        
		        FacultyDTO dto = (FacultyDTO) populatedto(request);
		      //  System.out.println("do get.......");

		        String op = DataUtility.getString(request.getParameter("operation"));
		        
		        FacultyModel model = new FacultyModel();
		        
		      //  System.out.println("go geet.....2");
		        try {
		        	//list=model.list(pageNo, pageSize);
		            list = model.search(dto, pageNo, pageSize);
		            //ServletUtility.setList(list, request);
		           
		            if (list == null || list.size() == 0) 
			           
		            {
			                ServletUtility.setErrorMessage("No record found ", request);
			            }
		          // System.out.println("do get........3");
		            ServletUtility.setList(list, request);
		           
		            ServletUtility.setPageNo(pageNo, request);
		            
		            ServletUtility.setPageSize(pageSize, request);
		            
		            ServletUtility.forward(getView(), request, response);
		 //    System.out.println("do get...........4");
		        }
		       
		        catch (ApplicationException e) 
			        {
		        	 e.printStackTrace();
			            log.error(e);
			            ServletUtility.handleException(e, request, response);
			            return;
			        }
		        log.debug("SubjectListCtl doGet End");
		    }
	 
	  /**
	     * Contains Submit logic
	     */
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      
		 log.debug("FacultyListCtl doPost Start");
	        
	      //  List list = null;
	        List list=new ArrayList();
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	       
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader .getValue("page.size")) : pageSize;
	      
	      FacultyDTO dto = (FacultyDTO) populatedto(request);
	        
	       String op = DataUtility.getString(request.getParameter("operation"));
	       String[] ids = request.getParameterValues("ids");
	        FacultyModel model = new FacultyModel();

	        try {

	           /* if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                    || "Previous".equalsIgnoreCase(op)) 
	            
	            {

*/	                if (OP_SEARCH.equalsIgnoreCase(op)) 
		                {
		                    pageNo = 1;
		                } 
	                else if (OP_NEXT.equalsIgnoreCase(op))
		                {
		                    pageNo++;
		                } 
	                
	                else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) 
		                {
		                    pageNo--;
		                }
	                else if (OP_NEW.equalsIgnoreCase(op)) 
	                {
	                    ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
	                    return;
	                }
	                else if (OP_DELETE.equalsIgnoreCase(op))
	                {
	                    pageNo = 1;
	                    if (ids != null && ids.length > 0)
	                    {
	                        FacultyDTO deletedto = new FacultyDTO();
	                        for (String id : ids) 
	                        {
	                            deletedto.setId(DataUtility.getInt(id));
	                            model.delete(deletedto);
	                            
	                            ServletUtility.setSuccessMessage("Data is successfully delete", request);
	                        }
	                    } 
	                    else 
	                    {
	                        ServletUtility.setErrorMessage("Select at least one record", request);
	                    }
	                }
	                else if(OP_RESET.equalsIgnoreCase(op))
	                {
	                ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);	
	                	return;
	                }




	          //  }
	            
	            list = model.search(dto, pageNo, pageSize);
	            
	            ServletUtility.setList(list, request);
	            
	            if (list == null || list.size() == 0) 
		            {
		                ServletUtility.setErrorMessage("No record found ", request);
		            }
	            
	          //---
	    		ServletUtility.setDTO(dto, request);

	            
		            	ServletUtility.setList(list, request);

			            ServletUtility.setPageNo(pageNo, request);
			           
			            ServletUtility.setPageSize(pageSize, request);
			            
			            ServletUtility.forward(getView(), request, response);

	        } 
	        
	        catch (ApplicationException e) 
		        {
		            log.error(e);
		            ServletUtility.handleException(e, request, response);
		            return;
		        }
		        log.debug("FacultyListCtl doPost End");
	    }


	 
	
	@Override
	protected String getView() {
		
		return ORSView.FACULTY_LIST_VIEW;
	}

}
