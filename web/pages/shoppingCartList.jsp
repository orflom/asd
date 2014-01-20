
<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>
<content tag="heading"><fmt:message key="userList.heading"/></content>

<%-- For linking to edit screen --%>
<bean:struts id="editURL" forward="editUser"/>



<display:table name="${shoppingList.orderEntries}" cellspacing="0" cellpadding="0"
    requestURI="edit" defaultsort="1" export="false" id="parent"
    pagesize="25" styleClass="list userList">
	<display:column property="product.title" />
	<display:column property="totalSalePrice"
        decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
    <display:column 
    	headerClass="sortable" 
        titleKey="productForm.delete"
        href="deleteShoppingCart.html"
        paramId="productId"
        paramProperty="uid">Delete
    </display:column>
</display:table>

<display:table name="${shoppingList}" cellspacing="0" cellpadding="0"
    requestURI="edit" defaultsort="1" export="false" id="parent"
    pagesize="25" styleClass="list userList">
	<display:column property="orderNumber" 
		headerClass="sortable"
		titleKey="orderForm.ordernumber"/>
	<display:column property="salesTax" />
	<display:column property="total" 
	    decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
</display:table>

<div style="padding: 3px; margin: 3px; font-size: 12px; font-weight: bold"><a href="<c:url value="reviewCheckout.html"/>"> &nbsp;&nbsp;Proceed To Checkout &gt;&gt;</a></div>
<div style="padding: 3px; margin: 3px; font-size: 12px; font-weight: bold"><a href="<c:url value="browseBooks.html"/>"> &lt;&lt; Continue to Shop </a></div>
<c:out value="${buttons}" escapeXml="false" />
  
