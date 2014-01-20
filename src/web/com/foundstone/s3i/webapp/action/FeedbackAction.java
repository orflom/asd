package com.foundstone.s3i.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.model.Feedback;
import com.foundstone.s3i.model.Product;
import com.foundstone.s3i.service.CustomerOrderManager;
import com.foundstone.s3i.service.ProductsManager;
import com.foundstone.s3i.webapp.form.FeedbackForm;


/**
 * Implementation of <strong>Action </strong> that interacts with the {@link
 * CostomerOrderForm} and retrieves values. It interacts with the {@link
 * CustomerOrderManager} to retrieve/persist values to the database.
 * 
 * <p>
 * <a href="ShoppingCartAction.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @struts.action name="feedbackForm" path="/feedback" scope="request"
 *                validate="false" parameter="method" input="bookDetails.html"
 * 
 * @struts.action-forward name="list" path="/WEB-INF/pages/productDetails.jsp"
 * @struts.action-forward name="receipt" path="/WEB-INF/pages/receipt.jsp"
 * @struts.action-forward name="browseBooks" path="/browseBooks.html"
 */
public final class FeedbackAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ProductsManager prodMgr = (ProductsManager) getBean("productsManager");
		HttpSession session = request.getSession();
		Product product = (Product) session.getAttribute(Constants.CURRENT_PRODUCT);
    	//Product prod = prodMgr.getProduct(attribute);
		log.debug("I found this product: " + product);
		
		FeedbackForm feedForm = (FeedbackForm) form;
		Feedback fb = new Feedback();
		fb.setFeedback(feedForm.getFeedback());
		
		log.debug("Here is the feedback I think: " + fb);
		product.addFeedback(fb);
		//prodMgr.
		log.debug("saving: " + product);
		
		prodMgr.saveFeedback(product);
		return mapping.findForward("browseBooks");
	}

}