package com.foundstone.s3i.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.CustomerOrderManager;

/**
 * Action class to browse books.
 * 
 * 
 * @struts.action path="/browseOrders" validate="false"
 * @struts.action-forward name="previousPage" path="/"
 * 
 * @struts.action-forward name="list" path="/WEB-INF/pages/orderList.jsp"
 */
public final class BrowseOrdersAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CustomerOrderManager orderMgr = (CustomerOrderManager) getBean("customerOrderManager");
		String parameter = "";
		parameter = request.getParameter("userId");
		User user = new User();
		
		//For testing purposes.  To be removed...
		if (parameter != null && parameter.length() > 0) {
			user.setUsername(parameter);
		} else {
			user.setUsername(request.getUserPrincipal().getName());
		}
		//HttpSession session =request.getSession();
		//UserForm userForm =
		// (UserForm)session.getAttribute(Constants.USER_KEY);

		//user.setUsername(userForm.getUsername());

		List orders = orderMgr.getCustomerOrdersByUser(user);
		if (orders == null || orders.size() == 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"orders.missing"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}
		request.setAttribute(Constants.ORDER_LIST, orders);

		return mapping.findForward("list");

	}
}