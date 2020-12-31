package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 *
 * @author SunilOS
 * @version 1.0

 */
@WebServlet(name="UserRegistrationCtl",urlPatterns={"/UserRegistrationCtl"})

public class UserRegistrationCtl extends BaseCtl
{

    public static final String OP_SIGN_UP = "SignUp";

    private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

   
    @Override
    protected boolean validate(HttpServletRequest request)
    {

        log.debug("UserRegistrationCtl Method validate Started");

        boolean pass = true;

        String login = request.getParameter("login");
        String dob = request.getParameter("dob");

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.valid", "First Name "));
			pass = false;
		}
		

        if (DataValidator.isNull(request.getParameter("lastName")))
        {
            request.setAttribute("lastName",PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.valid", "Last Name "));
			pass = false;
		}

        if (DataValidator.isNull(login)) 
        {
            request.setAttribute("login",PropertyReader.getValue("error.require", "Login Id"));
            pass = false;
        }
        else if (!DataValidator.isEmail(login)) 
        {
            request.setAttribute("login",PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("password")))
        {
            request.setAttribute("password",PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }
        
        else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.pwd", "Password "));
			pass = false;
        }
        if (DataValidator.isNull(request.getParameter("confirmPassword"))) 
        {
            request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("gender"))) 
        {
            request.setAttribute("gender",PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(dob)) 
        {
            request.setAttribute("dob",PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } 
        else if (!DataValidator.isDate(dob))
        {
            request.setAttribute("dob",PropertyReader.getValue("error.date", "Date Of Birth"));
            pass = false;
        }
        if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))&& !"".equals(request.getParameter("confirmPassword"))) 
        {
            ServletUtility.setErrorMessage("Password and Confirm  Password does not matched matched.", request);

            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mob")))
        {
            request.setAttribute("mob",PropertyReader.getValue("error.require", "MobileNo"));
            pass = false;
        }
        log.debug("UserRegistrationCtl Method validate Ended");

        return pass;
    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) 
    {

        log.debug("UserRegistrationCtl Method populatedto Started");

        UserDTO dto = new UserDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));

      dto.setRoleId(RoleDTO.STUDENT);
       

        dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

        dto.setLogin(DataUtility.getString(request.getParameter("login")));

        dto.setPassword(DataUtility.getString(request.getParameter("password")));

        dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

        dto.setGender(DataUtility.getString(request.getParameter("gender")));

        dto.setDob(DataUtility.getDate(request.getParameter("dob")));

        dto.setMobileNo(DataUtility.getString(request.getParameter("mob")));
      //  populateDTO(dto, request);

        log.debug("UserRegistrationCtl Method populatedto Ended");

        return dto;
    }

    /**
     * Display concept of user registration
     */
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	
    	System.out.println("userregistrationctl/doget");
        log.debug("UserRegistrationCtl Method doGet Started");
        ServletUtility.forward(getView(), request, response);

    }

    
    /**
     * Submit concept of user registration
     */
    
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        System.out.println("in post method");
        log.debug("UserRegistrationCtl Method doPost Started");
        String op = DataUtility.getString(request.getParameter("operation"));
       
        UserModel model = new UserModel();
        
        long id = DataUtility.getLong(request.getParameter("id"));
        
        if (OP_SIGN_UP.equalsIgnoreCase(op)) 
        {
            UserDTO dto = (UserDTO) populateDTO(request);
            
            try 
            {
                long pk = model.registerUser(dto);
               // dto.setId(pk);
              //  request.getSession().setAttribute("Userdto", dto);
                
                ServletUtility.setDTO(dto, request);
                
                ServletUtility.setSuccessMessage("User has been succesfully registered", request);
                ServletUtility.forward(ORSView.USER_REGISTRATION_VIEW, request, response);
                return;
            } 
            catch (ApplicationException e)
            {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                //ServletUtility.forward(ORSView.ERROR_VIEW, request, response);
                return;
            } 
            catch (DuplicateRecordException e) 
            {
                log.error(e);
                ServletUtility.setDTO(dto, request);
                ServletUtility.setErrorMessage("Login id already exists",request);
                ServletUtility.forward(getView(), request, response);
            }
        }
        log.debug("UserRegistrationCtl Method doPost Ended");
    }

 
    @Override
    protected String getView() {
        return ORSView.USER_REGISTRATION_VIEW;
    }

}
