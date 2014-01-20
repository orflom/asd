<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="productList.title"/></title>
<content tag="heading"><fmt:message key="productList.heading"/></content>

<%-- For linking to edit screen --%>
<bean:struts id="editURL" forward="editUser"/>

<display:table name="${productList}" cellspacing="0" cellpadding="0"
    requestURI="" defaultsort="1" export="true" id="products"
    pagesize="10" styleClass="list userList">
  
    <%-- Table columns --%>
    <display:column property="title" sort="true" 
    	headerClass="sortable" 
        titleKey="productForm.title"
        width="70%"
        href="bookDetails.html"
        paramId="id"
        paramProperty="id"/>
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

    <display:setProperty name="export.excel.filename" value="Product List.xls"/>
    <display:setProperty name="export.csv.filename" value="Product List.csv"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />
<%--            
<script type="text/javascript">
<!--
highlightTableRows("products");
//-->
</script>

--%>