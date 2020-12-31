package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Base controller class of project. It contain (1) Generic operations (2)
 * Generic constants (3) Generic work flow
 *
 * @author SunilOS
 * @version 1.0
 
 */
public abstract  class BaseCtl extends HttpServlet {

		public static final String OP_UPDATE = "Update";
		
	    public static final String OP_SAVE = "Save";
	    public static final String OP_CANCEL = "Cancel";
	    public static final String OP_DELETE = "Delete";
	    public static final String OP_LIST = "List";
	    public static final String OP_SEARCH = "Search";
	    public static final String OP_VIEW = "View";
	    public static final String OP_NEXT = "Next";
	    public static final String OP_PREVIOUS = "Previous";
	    public static final String OP_NEW = "New";
	    public static final String OP_GO = "Go";
	    public static final String OP_BACK = "Back";
	    public static final String OP_LOG_OUT = "Logout";
	    public static final String OP_FORGET_PASSWORD="ForgetPwd";

	    /**
	     * Success message key constant
	     */
	 
	    public static final String MSG_SUCCESS = "success";
	    /**
	     * Error message key constant
	     */
	 
	    public static final String MSG_ERROR = "error";
		public static final String OP_RESET = "Reset";
		
		public static final String OP_EDIT = "edit";
	    
		 /**
	     * Validates input data entered by User
	     *
	     * @param request
	     * @return
	     */
		
	
	    protected boolean validate(HttpServletRequest request) 
	    {
	    	System.out.println("validate/baseCtl");
	        return true;
	    }
	    /**
	     * Loads list and other data required to display at HTML form
	     *
	     * @param request
	       */
	 
	    protected void preload(HttpServletRequest request)
	    {
	    	System.out.println("preload/BaseCtl");
	    }

	    /**
	     * Populates bean object from request parameters
	     *
	     * @param request
	     * @return
	     */
	    protected BaseDTO populateDTO(HttpServletRequest request) {
	       System.out.println("populTE/baseCtl");
	    	return null;
	    }

	  
	    @Override
	    protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	    
	    {
System.out.println("service/BaseCtl");
       preload(request);

       System.out.println("preload/Service/BaseCtl");
	        String op = DataUtility.getString(request.getParameter("operation"));

	       System.out.println("operation"+op);
	        if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op)
	        		&& !OP_RESET.equalsIgnoreCase(op)&& !OP_VIEW.equalsIgnoreCase(op)
	                && !OP_DELETE.equalsIgnoreCase(op))
	        {
	       
	        	System.out.println("validate/service/basectl");
	        	
	        	if (!validate(request))
	        	{
	        		System.out.println("populate..1/service/baseCtl");
	                BaseDTO dto = (BaseDTO) populateDTO(request);
	                ServletUtility.setDTO(dto, request);
	                System.out.println("populate..2/service/baseCtl");
	                ServletUtility.forward(getView(), request, response);
	                return;
	            }
	        }
	        System.out.println("super.service/basectl");
	        super.service(request, response);
	    }

	    /**
	     * Returns the VIEW page of this Controller
	     *
	     * @return
	     */
	 
	    
	    protected abstract String getView();

	}

	    

