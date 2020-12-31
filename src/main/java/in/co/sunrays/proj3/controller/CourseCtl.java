package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CourseModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Course functionality Controller. Performs operation for add, update, delete
 * and get Course
 *
 * @author SunilOS
 * @version 1.0
 
 */

@WebServlet(name="CourseCtl",urlPatterns={"/ctl/CourseCtl"})

public class CourseCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CourseCtl.class);
	  

	@Override
	protected boolean validate(HttpServletRequest request)
	{
		log.debug("CourseCtl Method validate Started");
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("name")))
		{
			request.setAttribute("name", PropertyReader.getValue("error.require","Course Name"));
			pass=false;
		}
		else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.valid", "Course Name "));
			pass = false;
		}
		
		
		if(DataValidator.isNull(request.getParameter("description")))
		{
			request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
			pass=false;
		}
	
		 if (DataValidator.isNull(request.getParameter("duration")))
		 {
	            request.setAttribute("duration",PropertyReader.getValue("error.require", "Duration"));
	            pass = false;
	        }

	        log.debug("CourseCtl Method validate Ended");

	        return pass;
	    }
	

	   
	@Override
	    protected BaseDTO populateDTO(HttpServletRequest request) 
	{

	        log.debug("CourseCtl Method populatedto Started");

	        CourseDTO dto = new CourseDTO();

	        dto.setId(DataUtility.getLong(request.getParameter("id")));
	        dto.setName(DataUtility.getString(request.getParameter("name")));
	        dto.setDescription(DataUtility.getString(request.getParameter("description")));
	        dto.setDuration(DataUtility.getString(request.getParameter("duration")));

	     //   populateDTO(dto, request);

	        log.debug("CourseCtl Method populatedto Ended");

	        return dto;
	    }

	 
	/**
     * Contains display logic
     */
    
	   protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	    {
	        log.debug("CourseCtl Method doGet Started");

	        System.out.println("In do get");

	        String op = DataUtility.getString(request.getParameter("operation"));

	       
	        CourseModel model = new CourseModel();

	        long id = DataUtility.getLong(request.getParameter("id"));
	       
	        if (id > 0 || op != null)
	        {
	            CourseDTO dto;
	            try 
	            {
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
	        log.debug("CourseCtl Method doGetEnded");
	    }

	  
	    
	   /**
	     * Contains Submit logic
	     */
	    
	    
	    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	    {
	        
	    	log.debug("CourseCtl Method doGet Started");

	        System.out.println("In do get");

	        String op = DataUtility.getString(request.getParameter("operation"));

	    
	        CourseModel model = new CourseModel();

	        long id = DataUtility.getLong(request.getParameter("id"));

	        if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equals(op))
	        {

	            CourseDTO dto =  (CourseDTO) populateDTO(request);

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
	                  //  dto.setId(pk);
	                    ServletUtility.setDTO(dto, request);
		                ServletUtility.setSuccessMessage("Data is successfully saved",request);

	                }

	               
	            } catch (ApplicationException e) 
	            {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } 
	            catch (DuplicateRecordException e) 
	            {
	                ServletUtility.setDTO(dto, request);
	                ServletUtility.setErrorMessage("Course already exists", request);
	            }

	        }
	        else if (OP_DELETE.equalsIgnoreCase(op)) 
	        {

	            CourseDTO dto  =  (CourseDTO) populateDTO(request);
	            try
	            {
	                model.delete(dto);
	                ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request,response);
	                return;
	                
	            } catch (ApplicationException e) 
	            {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }

	        } 
	        else if (OP_CANCEL.equalsIgnoreCase(op))
	        {

	            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
	            return;

	        }

	        ServletUtility.forward(getView(), request, response);

	        log.debug("CourseCtl Method doPOst Ended");
	    }

	 
	
	@Override
	protected String getView() {
		
		return ORSView.COURSE_VIEW;
	}

}
