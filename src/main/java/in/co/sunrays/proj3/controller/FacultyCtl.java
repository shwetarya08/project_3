package in.co.sunrays.proj3.controller;

import java.io.IOException;
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
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModel;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.model.FacultyModel;
import in.co.sunrays.proj3.model.SubjectModel;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Faculty functionality Controller. Performs operation for add, update, delete
 * and get Faculty
 *
 * @author SunilOS
 * @version 1.0
 
 */

@WebServlet(name="FacultyCtl",urlPatterns={"/ctl/FacultyCtl"})
public class FacultyCtl extends BaseCtl {
	
	
	 private static Logger log = Logger.getLogger(FacultyCtl.class);

	    @Override
	    protected void preload(HttpServletRequest request) {
	       
	    	  CourseModel cmodel = new CourseModel();
		        try {
		            List coursel = cmodel.list();
		            request.setAttribute("courseList",coursel);
		        } catch (ApplicationException e) {
		            log.error(e);
		        }

		        SubjectModel smodel = new SubjectModel();
		        try {
		            List subl = smodel.list();
		            request.setAttribute("subjectList", subl);
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
	 
	 
	 
	 protected boolean validate(HttpServletRequest request) {

         log.debug("UserCtl Method validate Started");

         boolean pass = true;
         
         //String login=request.getParameter("login");
        // String dob=request.getParameter("dob");
       
         
         
         

         if(DataValidator.isNull(request.getParameter("collegeId")))
         
         	{
         	request.setAttribute("collegeId", PropertyReader.getValue("error.require","College Name"));
         	
         	   pass = false;
         	}
       	
         
         if(DataValidator.isNull(request.getParameter("firstName")))
	            
      	{
      	request.setAttribute("firstName", PropertyReader.getValue("error.require","First Name"));
      	
      	   pass = false;
      	}
         else if (!DataValidator.isName(request.getParameter("firstName"))) {
 			request.setAttribute("firstName", PropertyReader.getValue("error.valid", "First Name "));
 			pass = false;
 		}
 		
      	
      if(DataValidator.isNull(request.getParameter("lastName")))
      	
      	{
      	request.setAttribute("lastName", PropertyReader.getValue("error.require","Last Name"));
      	
      		   pass = false;
      	}
      
      if(DataValidator.isNull(request.getParameter("subjectId")))
 	     
     	{
     	request.setAttribute("subjectId",PropertyReader.getValue("error.require","Subject Name"));
     		
     		   pass = false;
     	}
      
      if(DataValidator.isNull(request.getParameter("courseId")))
          
  	{
  	request.setAttribute("courseId",PropertyReader.getValue("error.require","Course Name"));
  		
  		   pass = false;
  	}
     

      if(DataValidator.isNull(request.getParameter("qualification")))
      
      	{
      	request.setAttribute("qualification",PropertyReader.getValue("error.require","Qualification"));
      		
      		   pass = false;
      	}
      
  	
      if(DataValidator.isNull(request.getParameter("emailId")))
      
      	{
      	request.setAttribute("emailId",PropertyReader.getValue("error.require","Email Id"));
      		
      		   pass = false;
      	}
      
      
  	
      if(DataValidator.isNull(request.getParameter("dob")))
      
      	{
      	request.setAttribute("dob",PropertyReader.getValue("error.require","DOB"));
      		
      		   pass = false;
      	}
      
      if(DataValidator.isNull(request.getParameter("mobNo")))
          
  	{
  	request.setAttribute("mobNo",PropertyReader.getValue("error.require","MOBILE"));
  		
  		   pass = false;
  	}
      else if (!DataValidator.isMobile(request.getParameter("mobNo"))) {
			request.setAttribute("mobNo", PropertyReader.getValue("error.roll", "Mobile Number "));
			pass = false;
		}
		

           
   
      
	         				return pass;
	 } 
	            
	            protected BaseDTO populateDTO(HttpServletRequest request) {

		            log.debug("UserCtl Method populateDTO Started");

		            FacultyDTO dto = new FacultyDTO();

		           dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		          
		            

		            dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		            dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		            
		            dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		            dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		    		
		            dto.setQualification(DataUtility.getString(request.getParameter("qualification")));
		            dto.setEmailId(DataUtility.getString(request.getParameter("emailId")));
		            dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		            System.out.println(request.getParameter("dob"));
		            dto.setMobNo(DataUtility.getString(request.getParameter("mobNo")));
		          
		            dto.setId(DataUtility.getLong(request.getParameter("id")));
		                 //  dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		            
		           //  populateDTO(dto, request);

		            log.debug("FacultyCtl Method populateDTO Ended");

		            return dto;
		        }
	            
	            /**
	             * Contains display logic
	             */
	           
	            protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		            
		        	log.debug("FacultyCtl Method doGet Started");
		            
		        	String op = DataUtility.getString(request.getParameter("operation"));
		            // get model
		            
		        	FacultyModel model = new FacultyModel();
		            
		        	long id = DataUtility.getLong(request.getParameter("id"));
		            
		        	if (id > 0 || op != null) {
		               
		        		System.out.println("in id > 0  condition"+id);
		                
		        		FacultyDTO dto;
		                
		        		try {
		        			System.out.println("faculty do get.......101");
		                    dto = model.findByPK(id);
		                    ServletUtility.setDTO(dto, request);
		                    System.out.println("faculty do get.......102");
		                    } 
		        		
		        		catch (ApplicationException e) 
			        		{
			                    log.error(e);
			                    ServletUtility.handleException(e, request, response);
			                    return;
			                }
		            	}
		            
		
			            ServletUtility.forward(getView(), request, response);
			            log.debug("FacultyCtl Method doGet Ended");
			        }
		        
	            /**
	             * Contains Submit logic
	             */
	            
	            protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	 	           
		        	log.debug("FacultyCtl Method doPost Started");
		            
		        	String op = DataUtility.getString(request.getParameter("operation"));
		            // get model
		            
		        	FacultyModel model = new FacultyModel();
		            
		        	long id = DataUtility.getLong(request.getParameter("id"));
		            
		        	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op))
		        	{
		               
		        		FacultyDTO dto = (FacultyDTO) populateDTO(request);

		                try {
		                    if (id > 0) 
			                    {
		                    	System.out.println("in update faculty");
			                        model.update(dto);
			                        ServletUtility.setDTO(dto, request);
				                    ServletUtility.setSuccessMessage("Data is successfully updated",request);
				                  
			                    } 
		                    
		                    else 
		                    
			                    {
			                        long pk = model.add(dto);
			                       // dto.setId(pk);
			                        ServletUtility.setDTO(dto, request);
				                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
				                  
			                    }
		                    
		                	} 
		                
		                catch (ApplicationException e) 
			                {
			                    log.error(e);
			                    ServletUtility.handleException(e, request, response);
			                    return;
			                } 
		                
		                catch (DuplicateRecordException e) 
			                {
			                    ServletUtility.setDTO(dto, request);
			                    ServletUtility.setErrorMessage("Faculty already exists",request);
			                } catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		} 
		        	
		        	else if (OP_DELETE.equalsIgnoreCase(op))
			        	{
		System.out.println("delete.....1");
			                FacultyDTO dto = (FacultyDTO) populateDTO(request);
			                try {
			                    model.delete(dto);
			                    ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request,response);
			                    return;
			                } 
			                
			                catch (ApplicationException e) 
			                
				                {
			                    log.error(e);
			                    ServletUtility.handleException(e, request, response);
			                    return;
				                }

			        	}
		        	
		        			else if (OP_CANCEL.equalsIgnoreCase(op)) 
			        			{
				
					                ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
					                return;
			        			}
						            ServletUtility.forward(getView(), request, response);
					
						            log.debug("FacultyCtl Method doPostEnded");
						        }
	            
	            
	            
	            
	@Override
	protected String getView() {
		
		return  ORSView.FACULTY_VIEW;
	}

}
