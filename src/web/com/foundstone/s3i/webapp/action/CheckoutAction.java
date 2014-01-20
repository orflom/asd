package com.foundstone.s3i.webapp.action;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.model.CustomerOrder;
import com.foundstone.s3i.model.CustomerOrderItem;
import com.foundstone.s3i.model.User;
import com.foundstone.s3i.service.CustomerOrderManager;
import com.foundstone.s3i.service.PaymentManager;
import com.foundstone.s3i.service.ProductsManager;
import com.foundstone.s3i.service.UserManager;
import com.foundstone.s3i.webapp.form.CustomerOrderForm;

/**
 * Implementation of <strong>Action </strong> that interacts with the {@link
 * CostomerOrderForm} and retrieves values. It interacts with the {@link
 * CustomerOrderManager} to retrieve/persist values to the database.
 * 
 * <p>
 * <a href="ShoppingCartAction.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @struts.action name="customerOrderForm" path="/reviewCheckout"
 *                scope="request" validate="false" parameter="method"
 *                input="mainMenu"
 * @struts.action name="customerOrderForm" path="/approveCheckout"
 *                scope="request" validate="true" parameter="method"
 *                input="reviewCheckout.html"
 * @struts.action-forward name="list" path="/WEB-INF/pages/checkoutList.jsp"
 * @struts.action-forward name="receipt" path="/WEB-INF/pages/receipt.jsp"
 * @struts.action-forward name="browseBooks" path="/browseBooks.html"
 */
public final class CheckoutAction extends BaseAction {

	public ActionForward review(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering review()");

		HttpSession session = request.getSession();
		CustomerOrder shoppingCart = (CustomerOrder) session
				.getAttribute(Constants.SHOPPING_LIST);

		if (shoppingCart == null
				|| shoppingCart.getOrderEntries() == null 
				|| shoppingCart.getOrderEntries().size() <= 0) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"shoppingcart.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}

		processTotalPrice(shoppingCart);
		return mapping.findForward("list");
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

		float discount = retrieveCoupon(shoppingCart.getMagicCoupon());

		total = (1 + shoppingCart.getSalesTax()) * total * discount;
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

		shoppingCart.setTotal(numberFormat.format(total));
		shoppingCart.setTotalAmount(total);

	}

	/**
	 * @param magicCoupon
	 * @return
	 */
	protected float retrieveCoupon(String magicCoupon) {

		if (magicCoupon.length() != 10) {
			log
					.warn("one step closer to being hacked.  The magicCoupon length is"
							+ " "
							+ magicCoupon.length()
							+ " Magic coupon is: "
							+ magicCoupon);
			return 1;
		} else {

			String percentage = magicCoupon.substring(0, 2);
			String month = magicCoupon.substring(2, 4);
			String day = magicCoupon.substring(4, 6);
			String year = magicCoupon.substring(6, 10);

			String realPercentage = convertPair(percentage);
			String realMonth = convertPair(month);
			String realYear = convertPair(year);
			String realDay = convertPair(day);
			DateFormatUtils dateFormatUtils = new DateFormatUtils();
			Date date = new Date(System.currentTimeMillis());
			//date.
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			int _year = calendar.get(Calendar.YEAR);
			int _month = calendar.get(Calendar.MONTH);
			int _day = calendar.get(Calendar.DAY_OF_MONTH);

			log.debug("Year: " + _year);
			log.debug("Month: " + _month);
			log.debug("Day: " + _day);
			log.debug("percentage: " + percentage);

			log.debug("The coupon converted to: " + realPercentage + realMonth
					+ realDay + realYear);

			if (Integer.parseInt(realYear) > _year) {
				float f = Float.parseFloat(realPercentage);
				return (100 - f) / 100;//realPercentage;
			}

			if (Integer.parseInt(realMonth) > _month
					&& Integer.parseInt(realYear) == _year) {
				float f = Float.parseFloat(realPercentage);
				return (100 - f) / 100;//realPercentage;
			}

			if (Integer.parseInt(realDay) > _day
					&& Integer.parseInt(realMonth) > _month
					&& Integer.parseInt(realYear) == _year) {
				float f = Float.parseFloat(realPercentage);
				return (100 - f) / 100;//realPercentage;
			}
		}

		return 1;
	}

	/**
	 * @param letterPair
	 * @return
	 */
	private String convertPair(String letterPair) {
		log.debug("Letterpair: " + letterPair);
		letterPair = letterPair.replace('O', '0');
		letterPair = letterPair.replace('A', '1');
		letterPair = letterPair.replace('B', '2');
		letterPair = letterPair.replace('C', '3');
		letterPair = letterPair.replace('D', '4');
		letterPair = letterPair.replace('E', '5');
		letterPair = letterPair.replace('F', '6');
		letterPair = letterPair.replace('G', '7');
		letterPair = letterPair.replace('H', '8');
		letterPair = letterPair.replace('I', '9');

		return letterPair;
	}

	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering approve()");

		User user = new User();
		String username = request.getUserPrincipal().getName();
		UserManager userManager = (UserManager) getBean("userManager");
		user = userManager.getUser(username);

		log.debug("User: " + user + " entered checkout approve().");

		CustomerOrderForm orderForm = (CustomerOrderForm) form;

		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();

		CustomerOrder shoppingCart = (CustomerOrder) session
				.getAttribute(Constants.SHOPPING_LIST);

		if (shoppingCart == null) {
			log.debug("approve() called with empty shopping cart");
			mapping.findForward("mainMenu");
		}

		log.debug("saving " + shoppingCart.getOrderEntries().size()
				+ " entries");

		shoppingCart.setMagicCoupon(orderForm.getMagicCoupon());
		shoppingCart.setUser(user);
		processTotalPrice(shoppingCart);

		PaymentManager paymentManager = (PaymentManager) getBean("paymentManager");
		if (orderForm.getPaymentType().equals(Constants.PAYMENT_TYPE_CC)) {
			// CREDIT CARD PAYMENT
			shoppingCart.setCreditCardNumber(orderForm.getCreditCardNumber());
			shoppingCart.setExpiration(orderForm.getExpiration());
			try {
				paymentManager.processCreditCardPayment(shoppingCart
						.getCreditCardNumber(), shoppingCart.getExpiration(),
						new Double(shoppingCart.getTotalAmount()));
			} catch (Exception e) {
				log.debug(e.getMessage());
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"checkout.failedPayment"));
			}
		} else if (orderForm.getPaymentType().equals(
				Constants.PAYMENT_TYPE_BANK)) {
			// BANK ACCOUNT TRANSFER
			shoppingCart.setBankAccountNumber(orderForm.getBankAccountNumber());
			try {
				paymentManager.processBankAccountPayment(shoppingCart
						.getBankAccountNumber(), new Double(shoppingCart
						.getTotalAmount()));
			} catch (Exception e) {
				log.debug(e.getMessage());
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"checkout.failedPayment"));
			}
		} else {
			// INVALID PAYMENT TYPE - don't process order
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"checkout.invalidPaymentType"));
			saveMessages(request.getSession(), messages);
			return mapping.getInputForward();
		}

		if (messages.size() > 0) {
			saveMessages(request.getSession(), messages);
		}

		// say that things were successful whether payment succeeded or not
		shoppingCart.setShipped('Y');
		CustomerOrderManager orderManager = (CustomerOrderManager) getBean("customerOrderManager");
		orderManager.saveCustomerOrder(shoppingCart);

		session.setAttribute(Constants.SHOPPING_LIST, null);
		request.setAttribute(Constants.SHOPPING_LIST, shoppingCart);
		// return a forward to searching users
		return mapping.findForward("receipt");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering view()");
		User user = new User();
		String username = request.getUserPrincipal().getName();
		user.setUsername(username);

		CustomerOrderManager manager = (CustomerOrderManager) getBean("customerOrderManager");

		HttpSession session = request.getSession();
		CustomerOrder shoppingCart = (CustomerOrder) session
				.getAttribute(Constants.SHOPPING_LIST);

		if (shoppingCart == null) {
			log
					.debug("Shopping cart null.  Looks like the shoppingCart is empty.");
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