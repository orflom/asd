package com.foundstone.s3i.webapp.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.model.Role;
import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.UserManager;
import com.foundstone.s3i.webapp.util.RequestUtil;
import com.foundstone.s3i.webapp.util.SslUtil;

/**
 * This class is used to filter all requests to the <code>Action</code>
 * servlet and detect if a user is authenticated. If a user is authenticated,
 * but no user object exists, this class populates the <code>UserForm</code>
 * from the user store.
 * 
 * <p>
 * <a href="ActionFilter.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author Matt Raible
 * @version $Revision: 1.2 $ $Date: 2005/08/01 20:11:38 $
 * 
 * @web.filter display-name="Action Filter" name="actionFilter"
 * 
 * <p>
 * Change this value to true if you want to secure your entire application. This
 * can also be done in web-security.xml by setting <transport-guarantee>to
 * CONFIDENTIAL.
 * </p>
 * 
 * @web.filter-init-param name="isSecure" value="${secure.application}"
 */
public class ActionFilter implements Filter {
	private static Boolean secure = Boolean.FALSE;

	private final transient Log log = LogFactory.getLog(ActionFilter.class);

	private FilterConfig config = null;

	public void init(FilterConfig config) throws ServletException {
		this.config = config;

		/* This determines if the application uconn SSL or not */
		secure = Boolean.valueOf(config.getInitParameter("isSecure"));
	}

	/**
	 * Destroys the filter.
	 */
	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// cast to the types I want to use
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(true);

		// do pre filter work here
		// If using https, switch to http
		String redirectString = SslUtil.getRedirectString(request, config
				.getServletContext(), secure.booleanValue());

		if (redirectString != null) {
			if (log.isDebugEnabled()) {
				log.debug("protocol switch needed, redirecting to '"
						+ redirectString + "'");
			}

			// Redirect the page to the desired URL
			response.sendRedirect(response.encodeRedirectURL(redirectString));

			// ensure we don't chain to requested resource
			return;
		}

		User user = (User) session.getAttribute(Constants.USER_KEY);
		ServletContext context = config.getServletContext();
		String username = request.getRemoteUser();
		if (user != null) {
			Set roles = user.getRoles();
			Role role = new Role();
			Iterator iter = roles.iterator();
			while (iter.hasNext()) {
				role = (Role) iter.next();
				if (role.getName().equals("admin")) {
					RequestUtil.setCookie(response, "isAdmin", "true", "/");
				}
			}
		}

		//if()
		//Cookie c =

		// user authenticated, empty user object
		if ((username != null) && (user == null)) {
			ApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(context);

			UserManager mgr = (UserManager) ctx.getBean("userManager");
			user = mgr.getUser(username);
			session.setAttribute(Constants.USER_KEY, user);

			// if user wants to be remembered, create a remember me cookie
			if (session.getAttribute(Constants.LOGIN_COOKIE) != null) {
				session.removeAttribute(Constants.LOGIN_COOKIE);

			}
		}

		chain.doFilter(request, response);
	}
}