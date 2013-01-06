package org.arquillian.tck.container.servlet_3_0;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Servlet that echo the given @text@ request parameter 
 *
 */
@WebServlet(urlPatterns = {"/echo"})
public class EchoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static String TEXT_PARAM = "text";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getParameter(TEXT_PARAM) == null) {
			throw new IllegalArgumentException("Missing mendatory argument: " + TEXT_PARAM);
		}
		
		resp.getWriter().write(req.getParameter(TEXT_PARAM));
	}
}
