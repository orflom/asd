<%@ include file="/common/taglibs.jsp"%>

<div id="passwordHintForm">
	<form method="get" id="passwordHintForm" action="<c:url value="/passwordHint.html"/>" ">

		<div>
			Username:
		</div>
		<input type="text" name="username" 
				id="username" size="9" tabindex="1" class="txtLoginBox"/>
		<input type="submit"/>
	</form>

</div>
