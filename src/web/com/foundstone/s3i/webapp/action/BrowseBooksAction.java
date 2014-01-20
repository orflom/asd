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
 * Action class to browse books.
 * 
 * 
 * @struts.action path="/browseBooks" validate="false"
 * @struts.action-forward name="previousPage" path="/"
 * 
 * @struts.action-forward name="list" path="/WEB-INF/pages/productList.jsp"
 */
public final class BrowseBooksAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();

		ProductsManager prodMgr = (ProductsManager) getBean("productsManager");
		List products = prodMgr.getProductsWithoutDescriptions();

		request.setAttribute(Constants.PRODUCT_LIST, products);

		return mapping.findForward("list");

	}
}