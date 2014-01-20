<%@ include file="/common/taglibs.jsp"%>

<c:if test="${not empty param.error}">
  <div class="error">
    Invalid login.  Please try again or click the "Forgot your Password?" link.
  </div>
</c:if>

<div class="welcome">
<span style="font-size: 12px; font-weight: bold;">W</span>elcome to HacmeBooks!  While we would love to think that this bookstore is the most secure site on the planet, this is unfortunately not true.  
You are challenged to identify the following vulnerabilies:
<ul>
	<li>SQL Injection</li>
	<li>Cross Site Scripting</li>
	<li>Broken Authorization</li>
	<li>Weak Passwords</li>
	<li>Inproper Use of Crypto</li>
</div>
<div>Hacme Books (TM) is a software security training application provided by Foundstone Professional Services. This application is designed to teach application developers, programmers, architects and security professionals how to create secure software. Hacme Books is used extensively in Foundstone's Writing Secure Code - Java class where students are challenged to find the vulnerabilities and the fix the application by re-writing its code. </div>

</div>

<div style="padding: 5px; margin: 5px; font-size: 12px; font-weight: bold; border: 1px #E8E8E8 solid"> Featured Books </div>


<%
	try	{
	com.foundstone.s3i.webapp.action.BookActionMainPage bookActionMainPage = new com.foundstone.s3i.webapp.action.BookActionMainPage();
	bookActionMainPage.populateRequestWithHitList(request);
	//out.write("Working with live books");
	%>
<c:forEach var="book" items="${hotlist}">
	<div style="clear: left; margin-bottom: 10px ;border-bottom: solid #E8E8E8 1px; margin: 6px; padding: 3px;">
		<div style="font-size: 12px; padding: 3px; float: left; "><img height="70" src="<c:out value="${book.imgUrl}"/>"/></div>
		<div style="font-size: 12px; padding: 3px;"><c:out value="${book.title}"/></div>
		<div style="font-size: 12px; padding: 3px;"><a href="<c:url value="bookDetails.html?id=${book.id}"/>">Details</a></div>
	</div>
</c:forEach>
<%
	} catch (Exception e){
	//FIXME: for some reason, line 81 of BaseAction.java throws NullPointerException, causing this catch block to execute.
	//       It must be that the Struts servlet object is null, but why?  Seems to happen only when Tomcat first starts up.
	//       Issue resolves as soon as other pages have been called.
%>	
<div style="clear: left; margin-bottom: 10px ;border-bottom: solid #E8E8E8 1px; margin: 6px; padding: 3px;">

		<div style="font-size: 12px; padding: 3px; float: left; "><img height="70" src="images/books/0072227427.01._SCMZZZZZZZ_.jpg"/></div>
		<div style="font-size: 12px; padding: 3px;">Hacking Exposed: Network Security Secrets &amp; Solutions, Fourth Edition (Hacking Exposed)</div>
		<div style="font-size: 12px; padding: 3px;"><a href="bookDetails.html?id=735">Details</a></div>
	</div>

	<div style="clear: left; margin-bottom: 10px ;border-bottom: solid #E8E8E8 1px; margin: 6px; padding: 3px;">
		<div style="font-size: 12px; padding: 3px; float: left; "><img height="70" src="images/books/0735615888.01._SCMZZZZZZZ_.jpg"/></div>

		<div style="font-size: 12px; padding: 3px;">Writing Secure Code (With CD-ROM)</div>
		<div style="font-size: 12px; padding: 3px;"><a href="bookDetails.html?id=823">Details</a></div>
	</div>

	<div style="clear: left; margin-bottom: 10px ;border-bottom: solid #E8E8E8 1px; margin: 6px; padding: 3px;">
		<div style="font-size: 12px; padding: 3px; float: left; "><img height="70" src="images/books/0735618429.01._SCMZZZZZZZ_.jpg"/></div>
		<div style="font-size: 12px; padding: 3px;">Improving Web Application Security: Threats and Countermeasures</div>
		<div style="font-size: 12px; padding: 3px;"><a href="bookDetails.html?id=1469">Details</a></div>

	</div>
<%
	}
%>

<div class="separator" style="clear: left"></div>

<div style="margin: 0px;padding:0px;">
<%
out.write(com.foundstone.s3i.webapp.action.Xml2html.spew("http://localhost:8989/HacmeBooks/xsl/info.xsl","http://www.foundstone.com/home/info.xml"));
%>
</div>