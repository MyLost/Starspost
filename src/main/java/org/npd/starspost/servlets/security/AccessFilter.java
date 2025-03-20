package org.npd.starspost.servlets.security;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 *
 * @author midas
 */
@WebFilter(urlPatterns = {
	"/*"
})
public class AccessFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
// logger.info("Access filter starting...");
		/*
		try {
			logger.info("Password for admin" + SecurityController.computeSaltedBase256Hash("admin", "SHA-256", ",.,.,@#@>,."));
			System.out.println(new UserJPADao().findAll());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 */

		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if(SecurityController.checkRemoteAdress(request, response)) {
				request.getRequestDispatcher("/pages/common/authError.jsp").forward(request, response);
			} else if (!SecurityController.checkAuth(httpRequest, httpResponse)) {
				chain.doFilter(request, response);
			} else if (httpRequest.getRequestURI().endsWith("/starspost/")) {
				httpResponse.sendRedirect("index.html");
			} else if (httpRequest.getRequestURI().endsWith("/starspost/index.html") ||
				httpRequest.getRequestURI().endsWith("starspost/pages/styles/style.css") ||
				httpRequest.getRequestURI().endsWith("starspost/pages/scripts/register.js") ||
				httpRequest.getRequestURI().endsWith("starspost/pages/common/login.jsp") ||
				httpRequest.getRequestURI().endsWith("starspost/pages/common/register.jsp") ||
				httpRequest.getRequestURI().endsWith("starspost/LoginController") ||
				httpRequest.getRequestURI().endsWith("starspost/RegisterController") ||
				httpRequest.getRequestURI().endsWith("starspost/ExitController")
				) {
				chain.doFilter(request, response);
			} else if(httpRequest.getRequestURI().startsWith("http://localhost:8090/starspost")) {
				chain.doFilter(request, response);
			}else {
				chain.doFilter(request, response);
				//System.out.println("Ooops something crash");
				//request.getRequestDispatcher("/pages/common/crash.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void destroy() {
	}
}
