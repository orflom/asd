<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>
<content tag="heading"><fmt:message key="userList.heading"/></content>

<%-- For linking to edit screen --%>
<bean:struts id="editURL" forward="editUser"/>
<!-- Remove -->
<!-- For testing purposes this page accepts a userId parameter -->
<display:table name="${orderList}" cellspacing="0" cellpadding="0"
    requestURI="" defaultsort="1" export="true" id="parent"
    pagesize="25" styleClass="list userList">
    <display:column property="orderNumber" sort="false"
    	headerClass="sortable"
    	titleKey="orderForm.ordernumber"/>
    <display:column property="creditCardNumber" sort="false"
    	headerClass="sortable"
    	/>
	<display:column titleKey="orderForm.entries">
		<display:table name="${orderList[(parent_rowNum - 1)].orderEntries}" cellspacing="0" cellpadding="0">
			<display:column property="product.title" titleKey="productForm.title"/>
			<display:column property="totalSalePrice" titleKey="productForm.price"
			        decorator="com.foundstone.s3i.webapp.util.HacmeColumnDecorator"/>
		</display:table>
			
	</display:column>

    <display:setProperty name="paging.banner.item_name" value="product"/>
    <display:setProperty name="paging.banner.items_name" value="products"/>

    <display:setProperty name="export.excel.filename" value="Product List.xls"/>
    <display:setProperty name="export.csv.filename" value="Product List.csv"/>

</display:table>

<c:out value="${buttons}" escapeXml="false" />
            
<script type="text/javascript">
<!--
highlightTableRows("users");
//-->
</script>
