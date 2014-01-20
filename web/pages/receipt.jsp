
<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>
<content tag="heading"><fmt:message key="userList.heading"/></content>

<%-- For linking to edit screen --%>
<bean:struts id="editURL" forward="editUser"/>

<div>Thanks for shopping with HacmeBooks.  
You can be assured that your order was received 
and will be processed when we get around to it.</div>


<div>Receipt</div>
<div>-------</div>
<div>Total: <c:out value="${shoppingList.total}"/></div></div>
<div>Credit Card: <c:out value="${shoppingList.creditCardNumber}"/></div>
<div>Expiration:  <c:out value="${shoppingList.expiration}"/></div></div>
<div>Bank Account:  <c:out value="${shoppingList.bankAccountNumber}"/></div></div>

<c:out value="${buttons}" escapeXml="false" />
            
<script type="text/javascript">
<!--
highlightTableRows("users");
//-->
</script>
