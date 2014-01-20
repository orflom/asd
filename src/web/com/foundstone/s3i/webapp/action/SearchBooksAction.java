package com.foundstone.s3i.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.foundstone.s3i.Constants;
import com.foundstone.s3i.service.ProductsManager;

/**
 * Action class to browse books.
 * 
 * 
 * @struts.action path="/searchBooks" validate="false"
 * @struts.action-forward name="previousPage" path="/"
 * 
 * @struts.action-forward name="list" path="/WEB-INF/pages/productList.jsp"
 */
public final class SearchBooksAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();

		ProductsManager prodMgr = (ProductsManager) getBean("productsManager");
		String _keyWords = request.getParameter("keyWords");

		String[] words = createSearchCriteria(_keyWords);

		//log.debug("String Array: " + words[0]);
		if(words.length==0) {
			messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"search.empty"));
			saveMessages(request.getSession(), messages);
			return mapping.findForward("mainMenu");
		}
		
		List products = prodMgr.getProductByKeywords(words);

		request.setAttribute(Constants.PRODUCT_LIST, products);
		
		return mapping.findForward("list");
		
		
	}

	/**
	 * @param _keyWords
	 * @return
	 */
	public static String[] createSearchCriteria(String _keyWords) {
		//Break em up by spaces
		StringTokenizer tokenizer = new StringTokenizer(_keyWords, " ");
		ArrayList keyWords = new ArrayList();
		while (tokenizer.hasMoreTokens()) {
			keyWords.add(tokenizer.nextToken());
		}

		String words[] = new String[keyWords.size()];

		Iterator iter = keyWords.iterator();

		int index = 0;

		while (iter.hasNext()) {
			String element = (String) iter.next();
		
			words[index] = element.replace('+', ' ');
			index++;
		}
		return words;
	}
}