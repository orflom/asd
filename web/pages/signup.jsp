<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="signup.title"/></title>
<content tag="heading"><fmt:message key="signup.heading"/></content>
<body id="signup"/>

<fmt:message key="signup.message"/>

<div class="separator"></div>

<html:form action="/signup" focus="username" styleId="userForm"
    onsubmit="return validateUserForm(this)">

<table class="detail">
    <tr>
        <th>
            <HacmeBooks:label key="userForm.username"/>
        </th>
        <td>
            <html:text property="username" styleId="username" tabindex="101"/>
            <html:errors property="username"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.password"/>
        </th>
        <td>
            <html:password property="password" size="40"
                styleId="password" redisplay="true" tabindex="102"/>
            <html:errors property="password"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.confirmPassword"/>
        </th>
        <td>
            <html:password property="confirmPassword" size="40"
                styleId="confirmPassword" redisplay="true" tabindex="103"/>
            <html:errors property="confirmPassword"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.firstName"/>
        </th>
        <td>
            <html:text property="firstName" styleId="firstName" tabindex="104"/>
            <html:errors property="firstName"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.lastName"/>
        </th>
        <td>
            <html:text property="lastName" styleId="lastName" tabindex="105"/>
            <html:errors property="lastName"/>
        </td>
    </tr>





    <tr>
        <th>
            <HacmeBooks:label key="userForm.email"/>
        </th>
        <td>
            <html:text property="email" styleId="email" size="50" tabindex="106"/>
            <html:errors property="email"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.phoneNumber"/>
        </th>
        <td>
            <html:text property="phoneNumber" styleId="phoneNumber" tabindex="107"/>
            <html:errors property="phoneNumber"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.ssn"/>
        </th>
        <td>
            <html:text property="ssn" styleId="ssn" tabindex="108"/>
            <html:errors property="ssn"/>
        </td>
    </tr>
    <tr>
        <th>
            <HacmeBooks:label key="userForm.passwordHint"/>
        </th>
        <td>
            <html:text property="passwordHint"
                styleId="passwordHint" size="50" tabindex="109"/>
            <html:errors property="passwordHint"/>
        </td>
    </tr>
    <tr>
    	<td></td>
    	<td class="buttonBar">
            <html:submit styleClass="button" onclick="bCancel=false" tabindex="110">
            	  <fmt:message key="button.register"/>
            </html:submit>

            <html:cancel styleClass="button" onclick="bCancel=true" tabindex="111">
                <fmt:message key="button.cancel"/>
            </html:cancel>
        </td>
    </tr>
</table>

<div class="disclaimer">Note:  This information is not transmitted over the internet.  
By default, Hacme Books Server only listens on 'localhost' and stores data on your local machine.</div>
</html:form>

<html:javascript formName="userForm" cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript"
      src="<c:url value="/scripts/validator.jsp"/>"></script>