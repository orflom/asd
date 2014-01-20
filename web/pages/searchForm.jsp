<%@ include file="/common/taglibs.jsp"%>

<div id="loginTable">
<form method="post" action="<c:url value="/searchBooks.html"/>">

<table width="145" border="0" cellspacing="0" cellpadding="0" align="center">

	        <tr>
	          <td background="images/login/topbg.gif"><table width="130" border="0" cellspacing="3" cellpadding="3">

	            <tr>
	              <td><b>Search:</b></td>
	              <td>
	              	<input type="text" size="9" name="keyWords" id="keyWords" tabindex="4" class="txtLoginBox"/>
	              </tr>

	            <tr>
	              <td><span id="lblResult" class="errorMessage"></span></td>
	              <td><input type="submit" name="login" value="<fmt:message key="button.search"/>" tabindex="5" class="loginbutton"/></td>
	            </tr>

	            </table></td>
	          </tr>
	        <tr>
	          <td><img src="images/login/bottom.gif" width="145" height="6"></td>
	          </tr>

          </table>
</form>
</div>