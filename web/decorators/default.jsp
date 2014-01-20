
<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
        <%-- Include common set of meta tags for each layout --%>
        <%@ include file="/common/meta.jsp" %>
<head>

	<title>Hacme Books:</title><link href="styles/HacmeBooks.css" type="text/css" rel="stylesheet"></head>
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script> 
<body>

<table align="center" border="1" bordercolor="#000000" cellpadding="0" cellspacing="0" width="664">
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0"><tr><td><img src="images/hacme_header.jpg" height="32" width="664"></td></tr>
	<tr><td background="images/headerblank.jpg" height="19" width="664">

		<table align="right" border="0" cellpadding="0" cellspacing="0">
		  <tr>

		  <td><img src="images/header-firstspacer.gif" height="19" width="32"></td>
		  

		  <td class="hacmeheader" align="center" background="images/header-bgspacer.gif" valign="middle"><a href="mainMenu.html">Main</a></td>
		  <td><img src="images/header-midspacer.gif" height="19" width="41"></td>		  

		  <td class="hacmeheader" align="center" background="images/header-bgspacer.gif" valign="middle"><a href="browseBooks.html">Browse Books</a></td>
		  <td><img src="images/header-midspacer.gif" height="19" width="41"></td>		  
		  		  		  
<c:if test="${sessionScope.currentUserForm != null}">
		  <td class="hacmeheader" align="center" background="images/header-bgspacer.gif" valign="middle"><a href="browseOrders.html">My Orders</a></td>
		  <td><img src="images/header-midspacer.gif" height="19" width="41"></td>		  
		  
		  <td class="hacmeheader" align="center" background="images/header-bgspacer.gif" valign="middle"><a href="viewShoppingCart.html">View Cart</a></td>
		  <td><img src="images/header-midspacer.gif" height="19" width="41"></td>

		  <td class="hacmeheader" align="center" background="images/header-bgspacer.gif" valign="middle"><a href="reviewCheckout.html">Check Out</a></td>
		  <td><img src="images/header-midspacer.gif" height="19" width="41"></td>		  


		  <td class="hacmeheader" align="center" background="images/header-bgspacer.gif" valign="middle"><a href="logout.jsp">Log Out</a></td>
		  
		  <td background="images/header-bgspacer.gif"><img src="images/clear.gif" height="19" width="10"></td>
</c:if>
		</tr></table>

	</td></tr>
	<tr><td><table border="0" cellpadding="0" cellspacing="0" width="664">
	  <tr>
	    <td class="left_nav_table" valign="top" width="154"><p><img src="images/top_menu.jpg" height="31" width="154">


<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr class="menu_light">
		<td><img src="images/clear.gif" height="1" width="1"></td>
	</tr>
	<tr class="menu_dark">
		<td><table border="0" cellpadding="3" cellspacing="3">
				<tr>
					<td class="menu_dark">
						<img src="images/nav_arrows.gif">
						Search Books</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="menu_light">
		<td><img src="images/clear.gif" height="1" width="1"></td>
	</tr>

	<tr class="menu_light">
		<td><c:import url="/WEB-INF/pages/searchForm.jsp"/></td>
	</tr>
	<tr class="menu_light">
		<td><img src="images/clear.gif" height="1" width="1"></td>
	</tr>


	<tr>
		<td><img src="images/clear.gif" height="40" width="10"></td>
	</tr>

</table>
	<c:if test="${sessionScope.currentUserForm == null}">
		<c:import url="/WEB-INF/pages/loginForm.jsp"/>
	</c:if>

		        </p><p>&nbsp;</p></td>
	    <td width="1"><img src="images/clear.gif" height="31" width="1"></td>
	    <td valign="top"><img src="images/maingraphic.jpg" height="93" width="508">






          <table width="500" border="0" cellspacing="6" cellpadding="6" align="center">
            <tr>
              <td>

<!-- This is the Body -->
<%-- Error Messages --%>
<%@ include file="/common/messages.jsp" %>
            <decorator:body/>
                </td>
               </tr>
              </table>





			  <p>&nbsp;</p></td>
		  </tr>
		</table>

		</td>
	    </tr>
	  </table>

    </td>
  </tr>
</table>


<p class="Utxt1" align="center">Copyright 2006 Foundstone Professional Services, A Division of McAfee, Inc.</p><br>
</body></html>
