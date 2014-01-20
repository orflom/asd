
<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="checkoutList.title"/></title>
<content tag="heading"><fmt:message key="checkoutList.heading"/></content>

<%-- For linking to edit screen --%>
<bean:struts id="editURL" forward="editUser"/>

<display:table name="${shoppingList.orderEntries}" cellspacing="0" cellpadding="0"
    requestURI="edit" defaultsort="1" export="false" id="parent"
    pagesize="25" styleClass="list userList">
	<display:column property="product.title" />
	<display:column property="totalSalePrice" 
	    decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
</display:table>

<display:table name="${shoppingList}" cellspacing="0" cellpadding="0"
    requestURI="edit" defaultsort="1" export="false" id="parent"
    pagesize="25" styleClass="list userList">
	<display:column property="orderNumber" 
		headerClass="sortable"
		titleKey="orderForm.ordernumber"/>
	<display:column property="salesTax"
        decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
	<display:column property="total" 
	    decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
	
</display:table>
<c:out value="${buttons}" escapeXml="false" />

<html:form action="/approveCheckout" focus="creditCard" styleId="checkoutForm">

<table class="detail">
    <tr>
	    <td colspan="2">Please select payment method and account number:</td>
    </tr>
    <tr>
        <th><div class="checkoutForm">
			<%-- FIXME: HACK to set default for paymentType --%>
			<c:if test="${customerOrderForm.paymentType == null}">
			    <c:set target="${customerOrderForm}" property="paymentType" value="Credit Card Number"/>
			</c:if>
            <html:radio property="paymentType" value="Credit Card Number" title="Credit Card"/>
			<%= com.foundstone.s3i.Constants.PAYMENT_TYPE_CC %></div>
        </th>
        <td>
            <html:text property="creditCardNumber" size="40" 
            	styleId="creditcardnumber"/>
            <html:errors property="creditCardNumber"/>
        </td>
    </tr>
    <tr>
        <th><div class="checkoutForm">Expiration (MM-YY)</div></th>
        <td>
            <html:text property="expiration" size="16"
                styleId="expiration"/>
            <html:errors property="expiration"/>
        </td>
    </tr>
    <tr>
        <th><div class="checkoutForm">
            <html:radio property="paymentType" value="<%= com.foundstone.s3i.Constants.PAYMENT_TYPE_BANK %>" title="Bank Account"/>
			<%= com.foundstone.s3i.Constants.PAYMENT_TYPE_BANK %>
            </div>
        </th>
        <td>
            <html:text property="bankAccountNumber" size="40" 
            	styleId="bankaccountnumber"/>
            <html:errors property="bankAccountNumber"/>
        </td>
    </tr>
    <tr>
        <th>
            <div class="checkoutForm">Promotional Code (recieve 10-50% off)</div>
        </th>
        <td>
            <html:text property="magicCoupon" size="16"
                styleId="magicCoupon"/>
            <html:errors property="magicCoupon"/>
        </td>
    </tr>
    <tr>
    	<td></td>
    	<td class="buttonBar">
            <html:submit styleClass="button" onclick="bCancel=false">
            	  Purchase
            </html:submit>

            <html:cancel styleClass="button" onclick="bCancel=true">
                <fmt:message key="button.cancel"/>
            </html:cancel>
        </td>
    </tr>
</table>
</html:form>            
<script type="text/javascript">

</script>
