package com.foundstone.s3i.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.service.UserManager;
import com.foundstone.s3i.webapp.util.RequestUtil;


/**
 * <p>Intercepts Login requests for "Remember Me" functionality.</p>
 *
 * <p>
 * <a href="LoginFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/01 19:53:07 $
 *
 * @web.filter display-name="Login Filter" name="loginFilter"
 * @web.filter-init-param name="enabled" value="${rememberMe.enabled}"
 */
public final class LoginFilter implements Filter {
    //~ Instance fields ========================================================

    private transient final Log log = LogFactory.getLog(LoginFilter.class);
    private FilterConfig config = null;
    private boolean enabled = true;

    //~ Methods ================================================================

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain)
                  throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // See if the user has a remember me cookie
        Cookie c = RequestUtil.getCookie(request, Constants.LOGIN_COOKIE);

        WebApplicationContext context =
            (WebApplicationContext) config.getServletContext().getAttribute
            (WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        UserManager mgr = (UserManager) context.getBean("userManager");

        // Check to see if the user is logging out, if so, remove all
        // login cookies
        if (request.getRequestURL().indexOf("logout") != -1 &&
                request.getRemoteUser() != null) {
            // make sure user's session hasn't timed out
            if (request.getRemoteUser() != null) {
                if (log.isDebugEnabled()) {
                    log.debug("logging out '" + request.getRemoteUser() + "'");
                }

                RequestUtil.deleteCookie(response, c, request.getContextPath());
                request.getSession().invalidate();
            }
        } else if (c != null && enabled) {


        }

        chain.doFilter(req, resp);
    }

    /**
     * Initialize controller values of filter.
     */
    public void init(FilterConfig config) {
        this.config = config;

        String param = config.getInitParameter("enabled");
        enabled = Boolean.valueOf(param).booleanValue();

        if (log.isDebugEnabled()) {
            log.debug("Remember Me enabled: " + enabled);
        }

        config.getServletContext()
              .setAttribute("rememberMeEnabled",
                            config.getInitParameter("enabled"));
    }

    /**
     * destroy any instance values other than config *
     */
    public void destroy() {
    }
}
