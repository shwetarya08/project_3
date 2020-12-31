package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.model.RoleModel;
import in.co.sunrays.proj3.model.SubjectModel;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Subject functionality Controller. Performs operation for add, update, delete
 * and get Subject
 *
 * @author SunilOS
 * @version 1.0

 */
@WebServlet(name="SubjectCtl",urlPatterns={"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl {
	
	 private static final long serialVersionUID = 1L;

	    private static Logger log = Logger.getLogger(SubjectCtl.class);

	    @Override
	    protected void preload(HttpServletRequest request) {
	        CourseModel model = new CourseModel();
	        try 
	        {
	            List l = model.list();
	            request.setAttribute("courseList", l);
	        } catch (ApplicationException e) {
	            log.error(e);
	        }

	    }
	    

	    protected boolean validate(HttpServletRequest request) {

            log.debug("SubjectCtl Method validate Started");

            boolean pass = true;


            if(DataValidator.isNull(request.getParameter("name")))
            
            {
            	request.setAttribute("name", PropertyReader.getValue("error.require","Subject Name"));
            	
            	   pass = false;
            	}
            else if (!DataValidator.isName(request.getParameter("name"))) {
    			request.setAttribute("name", PropertyReader.getValue("error.valid", " Subject Name "));
    			pass = false;
    		}
    		
            	
            if(DataValidator.isNull(request.getParameter("description")))
            
            {
            	request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
            	
            		   pass = false;
            	}
            
        	
             	
            if(DataValidator.isNull(request.getParameter("courseId")))
            
            {
            	request.setAttribute("courseId", PropertyReader.getValue("error.require","Course Name"));
            	
            		   pass = false;
            		   
            	}
            
            
        /*    else if (DataValidator.isNotNull(request.getParameter("courseId"))) {
    			if ("----Select----".equals(request.getParameter("courseId"))) {
    				request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course name"));
    				pass = false;
    			}
    		}*/
            
            
            
            
            
            
            log.debug("SubjectCtl Method validate Ended");
			return pass;
            
	    }  
            
            
            protected BaseDTO populatedto(HttpServletRequest request) {

	            log.debug("SubjectCtl Method populatedto Started");

	            SubjectDTO dto = new SubjectDTO();

	            dto.setName(DataUtility.getString(request.getParameter("name")));

	            dto.setDescription(DataUtility.getString(request.getParameter("description")));

	           dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

	          //  dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
	           dto.setId(DataUtility.getLong(request.getParameter("id")));
	            log.debug("SubjectCtl Method populatedto Ended");

	            return dto;

            }
            
            
            /**
             * Contains display logic
             */
 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	            
	        	log.debug("SubjectCtl Method doGet Started");
	            
	        	String op = DataUtility.getString(request.getParameter("operation"));
	            // get model
	        	SubjectModel model = new SubjectModel();
	        	long id = DataUtility.getLong(request.getParameter("id"));
	        	
	            
	        	
	            
	        	if (id > 0 || op != null) {
	               
	        		System.out.println("in id > 0  condition");
	                
	        		SubjectDTO dto;
	                
	        		try {
	                    dto = model.findByPK(id);
	                    ServletUtility.setDTO(dto, request);
	                    } 
	        		
	        		catch (ApplicationException e) 
		        		{
		                    log.error(e);
		                    ServletUtility.handleException(e, request, response);
		                    return;
		                }
	            	}
	            
	
		            ServletUtility.forward(getView(), request, response);
		            log.debug("SubjectCtl Method doGet Ended");
		        }
	        
 
 
 /**
  * Contains Submit logics
  */
 protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
 {
     //System.out.println("........sava");
 	log.debug("SubjectCtl Method doPost Started");
     
 	String op = DataUtility.getString(request.getParameter("operation"));
     // get model
     
 	SubjectModel model = new SubjectModel();
     
 	long id = DataUtility.getLong(request.getParameter("id"));
     
 	if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
        
 		SubjectDTO dto = (SubjectDTO) populatedto(request);

         try {
        	 
             if (id > 0) 
	             {
	                 model.update(dto);
	                 ServletUtility.setDTO(dto, request);
	                 ServletUtility.setSuccessMessage("Data is successfully updated",request);
	             } 
             
             else
             
	             {
	                 long pk = model.add(dto);
	                 ServletUtility.setDTO(dto, request);
	                 ServletUtility.setSuccessMessage("Data is successfully saved",request);
	                 ///dto.setId(pk);
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
                 ServletUtility.setErrorMessage("Subject already exists",request);
             }
 		} 
 	
 	else if (OP_DELETE.equalsIgnoreCase(op))
     	{

             SubjectDTO dto = (SubjectDTO) populateDTO(request);
             try {
                 model.delete(dto);
                 ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request,response);
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
	
		                ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
		                return;
     			}
			            ServletUtility.forward(getView(), request, response);
		
			            log.debug("SubjectCtl Method doPostEnded");
			      }

	@Override
	protected String getView() {
		
		return ORSView.SUBJECT_VIEW;
	}

}
