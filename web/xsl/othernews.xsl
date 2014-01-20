<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited with XML Spy v4.2 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:template match="/">
  <html>
   <body>
   <table width="100%" border="0">
	<tr>
		<td>
			<table width="100%" border="1" align="center">
			<tr><td align="center"><font size="3">Other News</font></td></tr>
			<tr><td>
					<table width="100%" border="0">
						<tr align="left"><td width="34%">New Item</td>
						</tr>
						<tr><td  colspan="5"><hr width="100%" size="3" NOSHADE="true"></hr></td></tr>
						<xsl:for-each select="adv/advertise/othernews">
							<tr align="left">
								<td><xsl:value-of select="newitem"/></td>
								</tr>
							<tr><td  colspan="5"><hr></hr></td></tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
			</table>
		</td>
		</tr>
</table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>

  
  