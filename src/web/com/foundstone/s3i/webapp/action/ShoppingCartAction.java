package com.foundstone.s3i.webapp.action;

import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.RedirectingActionForward;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.CustomerOrderItem;
import com.foundstone.s3i.model.Product;
import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.CustomerOrderManager;
import com.foundstone.s3i.service.ProductsManager;

/**
 * Implementation of <strong>Action </strong> that interacts with the {@link
 * CustomerOrderForm} and retrieves values. It interacts with the {@link
 * CustomerOrderManager} to retrieve/persist values to the database.
 * 
 * <p>
 * <a href="ShoppingCartAction.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @struts.action name="customerOrderForm" path="/viewShoppingCart"
 *                scope="request" validate="false" parameter="method"
 *                input="mainMenu"
 * @struts.action name="customerOrderForm" path="/addShoppingCart"
 *                scope="request" validate="false" parameter="method"
 *                input="browseBooks"
 * @struts.action name="customerOrderForm" path="/deleteShoppingCart"
 *                scope="request" validate="false" parameter="method"
 *                input="browseBooks"
 * @struts.action-forward name="list" path="/WEB-INF/pages/shoppingCartList.jsp"
 * @struts.action-forward name="browseBooks" path="/browseBooks.html"
 */
public final class ShoppingCartAction extends BaseAction {

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering add()");

		User user = new User();
		String username = request.getUserPrincipal().getName();
		user.setUsername(username);

		String prodIdParam = request
				.getParameter(Constants.SHOPPING_CART_PRODUCT_ID);
		// productID should never be null...if it is we will redirect them to the
		// main menu
		if (prodIdParam == null) {
			log
					.error("No productid provided with request...redirecting to mainMenu");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"productid.missing"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		int prodId = Integer.parseInt(prodIdParam);
		
		ProductsManager prodManager = (ProductsManager) getBean("productsManager");

		HttpSession session = request.getSession();
		CustomerOrder shoppingCart = (CustomerOrder) session
				.getAttribute(Constants.SHOPPING_LIST);

		if (shoppingCart == null) {
			shoppingCart = new CustomerOrder();

			//TODO: Fix this - this is broken. Need to add a method in the DAO
			// to support order number generation!
			shoppingCart.setOrderNumber(System.currentTimeMillis());

			//TODO: Not everyone lives in Texas!
			shoppingCart.setSalesTax(0.0825);
			shoppingCart.setUser(user);
			shoppingCart.setOrderEntries(new ArrayList());
			session.setAttribute(Constants.SHOPPING_LIST, shoppingCart);
		}

		List orderEntries = shoppingCart.getOrderEntries();

		Product product = prodManager.getProduct(prodId);

		CustomerOrderItem item = new CustomerOrderItem();
		item.setProduct(product);
		item.setTaxPercentage(.0825F);
		item.setTotalSalePrice((float) product.getPrice());

		orderEntries.add(item);

		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"shoppingcart.item.added", product.getTitle()));
		saveMessages(request.getSession(), messages);
		log.debug("Loading forward: list");

		processTotalPrice(shoppingCart);
		//request.getRequestURL();
		ActionForward inputForward = mapping.getInputForward();
		String path = inputForward.getPath();
		//request.get
		//TODO: Fix this with a session bound parameter map.
		RedirectingActionForward forward = new RedirectingActionForward(path);
		log.debug("Redirecting to " + path + "hopefully");
		return forward;
	}

	/**
	 * @param shoppingCart
	 */
	private void processTotalPrice(CustomerOrder shoppingCart) {
		List orderEntries = shoppingCart.getOrderEntries();
		Iterator iter = orderEntries.iterator();
		double total = 0;
		while (iter.hasNext()) {
			CustomerOrderItem item = (CustomerOrderItem) iter.next();
			total += item.getTotalSalePrice();
		}
		total = (1 + shoppingCart.getSalesTax()) * total;
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

		shoppingCart.setTotal(numberFormat.format(total));

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering delete()");
		User user = new User();
		String username = request.getUserPrincipal().getName();
		user.setUsername(username);

		String itemUidParam = request
				.getParameter(Constants.SHOPPING_CART_PRODUCT_ID);

		HttpSession session = request.getSession();
		CustomerOrder shoppingCart = (CustomerOrder) session
				.getAttribute(Constants.SHOPPING_LIST);

		if (shoppingCart == null) {
			log.debug("Delete() called with empty shopping cart");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"shoppingcart.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		//This should never be null...if it is we will redirect them to the
		// main menu
		if (itemUidParam == null) {
			log
					.error("No productid provided with request...redirecting to mainMenu");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"productid.missing"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}
		long itemUid = 0;
		try {
			itemUid = Long.parseLong(itemUidParam);
		} catch (NumberFormatException e) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"productid.invalid"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		List orderEntries = shoppingCart.getOrderEntries();
		if (orderEntries == null || orderEntries.size() == 0) {
			log.debug("Delete() called with empty order entries");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"shoppingcart.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		List newOrderEntries = new ArrayList();
		Iterator orderIter = orderEntries.iterator();
		while (orderIter.hasNext()) {
			CustomerOrderItem item = (CustomerOrderItem) orderIter.next();
			long uid = item.getUid();
			if (uid != itemUid) {
				log.debug("Deleting " + item.getProduct().getTitle()
						+ "from shopping cart");
				newOrderEntries.add(item);
			}
		}

		shoppingCart.setOrderEntries(newOrderEntries);
		processTotalPrice(shoppingCart);
		if (newOrderEntries.size() <= 0) {
			// if cart is now empty, null the cart and send back to main menu
			shoppingCart.setOrderEntries(null);
			session.setAttribute(Constants.SHOPPING_LIST, null);
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"shoppingcart.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		} else {
			// return a forward to searching users
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering view()");
		User user = new User();
		String username = request.getUserPrincipal().getName();
		user.setUsername(username);

		HttpSession session = request.getSession();
		CustomerOrder shoppingCart = (CustomerOrder) session
				.getAttribute(Constants.SHOPPING_LIST);

		if (shoppingCart == null || shoppingCart.getOrderEntries() == null) {
			log
					.debug("Shopping cart null.  Looks like the shoppingCart is empty.");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"shoppingcart.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		if (shoppingCart.getOrderEntries().size() == 0) {
			log
					.debug("Shopping cart empty.  Looks like the shoppingCart is empty.");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"shoppingcart.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		log.debug("Loading forward: list");
		return mapping.findForward("list");
	}

}