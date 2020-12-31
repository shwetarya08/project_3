package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Main Controller performs session checking and logging operations before
 * calling any application controller. It prevents any user to access
 * application without login.
 *
 *
 * @author SunilOS
 * @version 1.0
 
 */
@WebFilter(filterName="FrontCtl",urlPatterns={"/ctl/*","/doc/*"})
public class FrontController implements Filter 
{

	public void destroy() {
		
		
	}

	 public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws 
	 IOException, ServletException
	 {

	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) resp;

	        HttpSession session = request.getSession(true);

	      // String role=(String) session.getAttribute("role");
	      
	     //  Object obj=session.getAttribute("user");
	       String uri=request.getRequestURI();
	       	 
           request.setAttribute("uri", uri);
        
	          
	        // 
	       
	        if (session.getAttribute("user") == null) 
	        {
	        	
	            request.setAttribute("message", "Your session has been expired. Please re-Login.");
	              ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
	            
	            return;
	            
	        } 
	        
	        else 
	        {
	            chain.doFilter(req, resp);
	        }
	    }

	    public void init(FilterConfig conf) throws ServletException {
	    }

	}