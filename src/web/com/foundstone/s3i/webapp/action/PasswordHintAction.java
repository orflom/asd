package com.foundstone.s3i.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.UserManager;


/**
 * Action class to send password hints to registered users.
 *
 * <p>
 * <a href="PasswordHintAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">David Raphael</a>
 *
 * @struts.action path="/passwordHint" validate="false"
 * 
 * @struts.action-forward name="previousPage" path="/"
 * @struts.action-forward name="hintPage" path="/WEB-INF/pages/showHint.jsp"
 * @struts.action-forward name="enterHint" path="/WEB-INF/pages/enterHint.jsp"
 */
public final class PasswordHintAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    throws Exception {
        MessageResources resources = getResources(request);
        ActionMessages errors = new ActionMessages();
        String username = request.getParameter("username");

        // ensure that the username has been sent
        if (username == null) {
            log.warn("Username not specified, notifying user that it's a required field.");

            errors.add(ActionMessages.GLOBAL_MESSAGE,
                       new ActionMessage("errors.required",
                                         resources.getMessage("userForm.username")));
            saveErrors(request, errors);
            return mapping.findForward("enterHint");
        }

        if (log.isDebugEnabled()) {
            log.debug("Processing Password Hint...");
        }

        ActionMessages messages = new ActionMessages();

        // look up the user's information
        try {
            UserManager userMgr = (UserManager) getBean("userManager");
            User user = userMgr.getUser(username);

            StringBuffer msg = new StringBuffer();
            msg.append("Your password hint is: " + user.getPasswordHint());
            //msg.append("\n\nLogin at: " + RequestUtil.getAppURL(request));
            
            request.getSession().setAttribute("passwordHint", msg);
            log.debug(msg);
            return mapping.findForward("hintPage");

        } catch (Exception e) {
            e.printStackTrace();
            // If exception is expected do not rethrow
            errors.add(ActionMessages.GLOBAL_MESSAGE,
                       new ActionMessage("login.passwordHint.error", username));
            saveMessages(request.getSession(), errors);;
        }

        return mapping.findForward("previousPage");
    }
}
