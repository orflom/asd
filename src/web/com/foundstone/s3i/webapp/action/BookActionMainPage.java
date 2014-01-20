package com.foundstone.s3i.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.service.ProductsManager;

/**
 * Action class to provide mainmenu books.
 * 
 * 
 * @struts.action path="/mainMenu" validate="false"
 * @struts.action-forward name="previousPage" path="/"
 * 
 * @struts.action-forward name="list" path="/WEB-INF/pages/mainMenu.jsp"
 */
public final class BookActionMainPage extends BaseAction {
	private static String[] QUICKLIST = { "Hacking Exposed Fourth",
			"Threats and Countermeasures", "Writing Secure Code" };

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		
		populateRequestWithHitList(request);
	
		return mapping.findForward("list");
	}

	/**
	 * @param request
	 */
	public void populateRequestWithHitList(HttpServletRequest request) {
		ProductsManager prodMgr = (ProductsManager) getBean("productsManager");
		
		String[] strings = SearchBooksAction.createSearchCriteria(QUICKLIST[0]);
		List productByKeywords = prodMgr.getProductByKeywords(strings);
	
		strings = SearchBooksAction.createSearchCriteria(QUICKLIST[2]);
	
		productByKeywords.addAll(prodMgr.getProductByKeywords(strings));
	
		strings = SearchBooksAction.createSearchCriteria(QUICKLIST[1]);
		productByKeywords.addAll(prodMgr.getProductByKeywords(strings));
		
		request.setAttribute(Constants.PRODUCT_HOTLIST, productByKeywords);
	
	}
}