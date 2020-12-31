package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.RoleModel;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })

public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		System.out.println("validate/LoginCtl");
		log.debug("LoginCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login))
		{
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		log.debug("LoginCtl Method validate Ended");
System.out.println("return pass/login ctl/validate");
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		System.out.println("popultedto/loginctl");
		log.debug("LoginCtl Method populatedto Started");

		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatedto Ended");

		return dto;
	}
	 /**
     * Display Login form
     *
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("doGet/loginctl");
		HttpSession session = request.getSession(true);
	
		log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		//String uri=request.getParameter("uri");
		/*UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0)
		{
			Userdto userdto;
			try {
				userdto = model.findByPK(id);
				ServletUtility.setdto(userdto, request);
			} 
			catch (ApplicationException e) 
			{
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}*/
		 if (OP_LOG_OUT.equals(op)) {

			session = request.getSession();

			
			session.invalidate();// sesion off
			ServletUtility.setSuccessMessage("You have Successfully Logout...", request);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);

			return;

		} 
		
			ServletUtility.forward(getView(), request, response);

				
		log.debug("UserCtl Method doPost Ended");

	}
	  /**
     * Submitting or login action performing
     *
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		System.out.println("dopost/loginctl");
		HttpSession session = request.getSession(true);
		log.debug(" Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		
		String uri=(String)request.getParameter("uri");

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserDTO dto = (UserDTO) populateDTO(request);

			try {

				dto = model.authenticate(dto.getLogin(), dto.getPassword());

				if (dto != null) {
					session.setAttribute("user", dto);
					
					long rollId = dto.getRoleId();

					RoleDTO roledto = role.findByPK(rollId);

					if (roledto != null) {
						session.setAttribute("role", roledto.getName());
					}

					if(uri==null || "null".equalsIgnoreCase(uri)){
						
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
						
					}
					else{
						ServletUtility.redirect(uri, request, response);
						
						//ServletUtility.forward(ORSView.WELCOME_CTL, request, response);
						return;
						
					}
					
					
					
					//ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
				
					
				}
				else {
					dto = (UserDTO) populateDTO(request);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			

		} 
		else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			
			System.out.println("ayushi ayi or disturb kra");
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}
		
		//System.out.println("forget/loginCtl");
		else if (OP_FORGET_PASSWORD.equalsIgnoreCase(op)) {

			
			System.out.println("forget/loginCtl");
			ServletUtility.redirect(ORSView.FORGET_PASSWORD_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		System.out.println("getView/loginCtl");
		return ORSView.LOGIN_VIEW;
	}

}
