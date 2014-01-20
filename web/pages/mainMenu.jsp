<%@ include file="/common/taglibs.jsp"%>
<%
	com.foundstone.s3i.webapp.action.BookActionMainPage bookActionMainPage = new com.foundstone.s3i.webapp.action.BookActionMainPage();
	bookActionMainPage.populateRequestWithHitList(request);
%>
<title><fmt:message key="mainMenu.title"/></title>
<content tag="heading"><fmt:message key="mainMenu.heading"/></content>

<div class="welcome" style="font-size: 11px;">
<b style="font-size: 12px">Y</b>ou have successfully logged into Hacme Books!  

<div>Hacme Books (TM) is a software security training application provided by Foundstone Professional Services. This application is designed to teach application developers, programmers, architects and security professionals how to create secure software. Hacme Books is used extensively in Foundstone's Writing Secure Code - Java class where students are challenged to find the vulnerabilities and the fix the application by re-writing its code.</div>

</div>

<div style="padding: 5px; margin: 5px; font-size: 12px; font-weight: bold; border: 1px #E8E8E8 solid"> Featured Books </div>

<c:forEach var="book" items="${hotlist}">
	<div style="clear: left; margin-bottom: 10px ;border-bottom: solid #E8E8E8 1px; margin: 6px; padding: 3px;">
		<div style="font-size: 12px; padding: 3px; float: left; "><img height="50" src="<c:out value="${book.imgUrl}"/>"/></div>
		<div style="font-size: 12px; padding: 3px;"><c:out value="${book.title}"/></div>
				<div style="font-size: 12px; padding: 3px;"><a href="<c:url value="bookDetails.html?id=${book.id}"/>">Details</a></div>
	</div>
</c:forEach>

<div class="separator" style="clear: left"></div>

<div style="margin: 0px;padding:0px;">
	<div>Please select one of the following Activities:</div>
	<div>
		<ul>
			<li><a href="browseBooks.html">Browse for Books</a></li>
			<li><a href="viewShoppingCart.html">View Shopping Cart</a></li>
			<li><a href="checkout.html">Checkout</a></li>
			<li><a href="browseOrders.html">View Past Orders</a></li>
		</ul>
	</div>
</div>
<div style="margin: 0px;padding:0px;">
<%
out.write(com.foundstone.s3i.webapp.action.Xml2html.spew("http://localhost:8989/HacmeBooks/xsl/info.xsl","http://www.foundstone.com/home/info.xml"));
%>
</div>