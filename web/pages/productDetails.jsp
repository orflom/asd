<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="productList.title"/></title>
<content tag="heading"><fmt:message key="productList.heading"/></content>

<%-- For linking to edit screen --%>
<bean:struts id="editURL" forward="editUser"/>

<div class="booktitle"><c:out value="${product.title}"/></div>
<div class="bookdetails"><span style="font-weight: bold;">Author(s): </span><c:out value="${product.author}"/></div>
<div class="bookdetails"><span style="font-weight: bold;">ISBN: </span><c:out value="${product.isbn}"/></div>
<div class="bookdetails"><img src="<c:url value="${product.imgUrl}"/>"/></div>

<display:table name="${product}" cellspacing="0" cellpadding="0"
    requestURI="" defaultsort="1" export="false" id="products"
    pagesize="10" styleClass="list userList">
  
    <%-- Table columns --%>
    <display:column property="title" sort="true" 
    	headerClass="sortable" 
        titleKey="productForm.title"
        width="70%"/>
    <display:column property="price" sort="true" 
    	headerClass="sortable" 
        titleKey="productForm.price"
        decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
    <c:if test="${pageContext.request.userPrincipal != null}">
     <display:column 
    	headerClass="sortable" 
        titleKey="productForm.add"
        href="addShoppingCart.html"
        paramId="productId"
        paramProperty="id">Add to Cart
     </display:column>
    </c:if>
	
    <display:setProperty name="paging.banner.item_name" value="product"/> 
    <display:setProperty name="paging.banner.items_name" value="products"/>
 	
</display:table>


<div class="booktitle">

<table class="feedback">
 <tr><th><b>Customer Feedback for this Product<br/><br/></b></th></tr>
 <c:if test="${product.feedback == null or empty product.feedback[0]}">
   <tr><td>Be the first to leave feedback!</td></tr>
 </c:if>
 <c:if test="${pageContext.request.userPrincipal == null}">
   <tr><td>(You must be logged in to leave feedback)<br/><br/></td></tr>
 </c:if>
 <c:forEach var="feedbackItem" items="${product.feedback}" >
  <tr><td><hr><jsp:getProperty name="feedbackItem" property="feedback"/></td></tr>
 </c:forEach> 
</table>

</div>

<c:if test="${pageContext.request.userPrincipal != null}">

<html:form action="/feedback" focus="feedback" styleId="checkoutForm">
<table class="detail">
    <tr>
        <th><div class="checkoutForm">
            Leave Feedback
</div>
        </th>
        <td>
            <html:textarea property="feedback"
            	styleId="feedback"
            	rows="15"
            	cols="45"/>
            <html:errors property="feedback"/>
        </td>
    </tr>


    <tr>
    	<td></td>
    	<td class="buttonBar">
            <html:submit styleClass="button" onclick="bCancel=false">
            	  Submit Feedback
            </html:submit>

            <html:cancel styleClass="button" onclick="bCancel=true">
                <fmt:message key="button.cancel"/>
            </html:cancel>
        </td>
    </tr>
</table>
</html:form>          

</c:if>

<c:out value="${buttons}" escapeXml="false" />
 <%--           
<script type="text/javascript">
<!--
highlightTableRows("products");
//-->
</script>
--%>
