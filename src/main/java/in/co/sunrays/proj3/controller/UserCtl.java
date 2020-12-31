package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.RoleModel;
import in.co.sunrays.proj3.model.UserModel;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * * User functionality Controller. Performs operation for add, update and get
 * User
 *
 * @author SunilOS
 * @version 1.0

 */

@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel model = new RoleModel();
		try {
			List l = model.list();

			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.valid", "First Name "));
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.valid", "Last Name "));
			pass = false;
		}


		if (DataValidator.isNull(login))
		{
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		
		
		if (DataValidator.isNull(request.getParameter("mobNo"))) {
			request.setAttribute("mobNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		}
		else if (!DataValidator.isMobile(request.getParameter("mobNo"))) {
			request.setAttribute("mobNo", PropertyReader.getValue("error.roll", "Mobile Number "));
			pass = false;
		}
		
		


		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		
		
		else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.pwd", "Password "));
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		
		else if 
		 (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("Confirm  Password  should not be matched.", request);
			pass = false;
		}


		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "RoleName"));
			pass = false;
		}
		
		
		
		log.debug("UserCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("UserCtl Method populatedto Started");

		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobNo")));
		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
        dto.setPassword(DataUtility.getString(request.getParameter("password")));
	//	populateDTO(dto, request);

		log.debug("UserCtl Method populatedto Ended");

		return dto;
	}

	 /**
     * Contains DIsplay logics
     */
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		log.debug("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));

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
		System.out.println(" after get view called");
		log.debug("UserCtl Method doGet Ended");
	}

	   /**
     * Contains Submit logics
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		log.debug("UserCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
		{
			UserDTO dto = (UserDTO) populateDTO(request);

			try {
				if (id > 0) 
				{
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
		
				} 
				else 
				{
					long pk = model.add(dto);
					//dto.setId(pk);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
		
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
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		}/* else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserDTO dto = (UserDTO) populatedto(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				

				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} */else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}
