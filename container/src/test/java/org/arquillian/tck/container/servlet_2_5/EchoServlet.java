package org.arquillian.tck.container.servlet_2_5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Servlet that echo the given @text@ request parameter 
 */
public class EchoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
     * The Query parameter to echo
     */
	private static String TEXT_PARAM = "text";
	
   /**
     * Echo the given text.
     *
     * @throws IllegalArgumentException on missing 'text' Query parameter
     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(req.getParameter(TEXT_PARAM) == null) {
			throw new IllegalArgumentException("Missing mendatory argument: " + TEXT_PARAM);
		}
		
		resp.getWriter().write(req.getParameter(TEXT_PARAM));
	}
}
