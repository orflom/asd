<%@ include file="/common/taglibs.jsp"%>

<div id="loginTable">
<%-- If you don't want to encrypt passwords programmatically, or you don't
     care about using SSL for the login, you can change this form's action
     to "j_security_check" --%>
<form method="post" id="loginForm" action="<c:url value="/authorize"/>" 
    onsubmit="saveUsername(this);return validateForm(this)">

<table width="145" border="0" cellspacing="0" cellpadding="0" align="center">
	        <tr>
	          <td><img src="images/login/top.gif" width="145" height="21"></td>
	          </tr>

	        <tr>
	          <td background="images/login/topbg.gif"><table width="130" border="0" cellspacing="3" cellpadding="3">

	            <tr>
	              <td><b>Username:</b></td>
	              <td>
	              <input type="text" name="j_username" id="j_username" size="9" tabindex="1" class="txtLoginBox"/>
	              </tr>
	            <tr>
	              <td><b>Password:</b></td>

	              <td>
                  <input type="password" name="j_password" id="j_password" size="9" tabindex="2" class="txtLoginBox" />
	              </tr>
	            <tr>
	              <td><span id="lblResult" class="errorMessage"></span></td>
	              <td><input type="submit" name="login" value="<fmt:message key="button.login"/>" tabindex="3" class="loginbutton"/></td>
	              </tr>
		     	  <tr>
						<td colspan="2"><br />
				            <fmt:message key="login.signup">
                			<fmt:param><c:url value="/signup.html"/></fmt:param>
				            </fmt:message>
        				</td>
				  </tr>
		     	  <tr>
						<td colspan="2"><br />
                		<a href="<c:url value="/passwordHint.html"/>">Forgot your password?</a>


        				</td>
				  </tr>

	            </table></td>
	          </tr>
	        <tr>
	          <td><img src="images/login/bottom.gif" width="145" height="6"></td>
	          </tr>

          </table>
</form>
</div>

<%@ include file="/scripts/login.js"%>
