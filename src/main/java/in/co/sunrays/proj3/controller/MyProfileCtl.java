package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * My Profile functionality Controller. Performs operation for update your
 * Profile
 *
 * @author SunilOS
 * @version 1.0
 
 */
@ WebServlet(name="MyProfileCtl",urlPatterns={"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl
{

    public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";

    private static Logger log = Logger.getLogger(MyProfileCtl.class);

    @Override
    protected boolean validate(HttpServletRequest request)
    {

        log.debug("MyProfileCtl Method validate Started");

        boolean pass = true;

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

            return pass;
        }

        if (DataValidator.isNull(request.getParameter("firstName"))) 
        {
            System.out.println("firstName" + request.getParameter("firstName"));
            request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("lastName")))
        {
            request.setAttribute("lastName",
                    PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("gender"))) 
        {
            request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mobileNo")))
        {
            request.setAttribute("mobileNo",
                    PropertyReader.getValue("error.require", "MobileNo"));
            pass = false;
        }

               
        if (DataValidator.isNull(request.getParameter("dob"))) 
        {
            request.setAttribute("dob",PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }
        
        log.debug("MyProfileCtl Method validate Ended");

        return pass;

    }

    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) 
    {
        log.debug("MyProfileCtl Method populatedto Started");

        UserDTO dto = new UserDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        																				
        dto.setLogin(DataUtility.getString(request.getParameter("login")));

        dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

        dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

        dto.setGender(DataUtility.getString(request.getParameter("gender")));

        
        
        
        dto.setDob(DataUtility.getDate(request.getParameter("dob")));
       // populateDTO(dto, request);
        return dto;
    }

    /**
     * Display Concept for viewing profile page view
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession(true);
        log.debug("MyprofileCtl Method doGet Started");

        UserDTO Userdto = (UserDTO) session.getAttribute("user");
        long id = Userdto.getId();
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        UserModel model = new UserModel();
        if (id > 0 || op != null) {
            System.out.println("in id > 0  condition");
            UserDTO dto;
            try {
                dto = model.findByPK(id);
                ServletUtility.setDTO(dto, request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);

        log.debug("MyProfileCtl Method doGet Ended");
    }

    /**
     * Submit Concept
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        log.debug("MyprofileCtl Method doPost Started");

        UserDTO Userdto = (UserDTO) session.getAttribute("user");
        long id = Userdto.getId();
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        UserModel model = new UserModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            UserDTO dto = (UserDTO) populateDTO(request);
            try {
                if (id > 0) {
                    Userdto.setFirstName(dto.getFirstName());
                    Userdto.setLastName(dto.getLastName());
                    Userdto.setGender(dto.getGender());
                    Userdto.setMobileNo(dto.getMobileNo());
                    Userdto.setDob(dto.getDob());
                    model.update(Userdto);

                }
                ServletUtility.setDTO(dto, request);
                ServletUtility.setSuccessMessage(
                        "Profile has been updated Successfully. ", request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e)
            {
                ServletUtility.setDTO(dto, request);
                ServletUtility.setErrorMessage("Login id already exists",request);
            }
        } else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op))
        {

            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request,
                    response);
            return;

        }

        ServletUtility.forward(getView(), request, response);

        log.debug("MyProfileCtl Method doPost Ended");
    }

    @Override
    protected String getView() 
    {
        return ORSView.MY_PROFILE_VIEW;
    }

}