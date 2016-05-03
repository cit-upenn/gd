<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  	<xsl:for-each select="entry_list/entry">
    <h3><b><xsl:value-of select="term/hw"/></b>[<em><xsl:value-of select="fl"/></em>]</h3>
    <ol>
    	<xsl:for-each select="sens">
    		<li><xsl:value-of select="mc"/></li>
    		<p><em>e.g. <xsl:value-of select="vi"/></em></p>
    		<xsl:for-each select="syn">
    		<p><b>Synonyms: </b><a style="color:red;"><xsl:value-of select="."/></a></p>
    		</xsl:for-each>
    		<xsl:for-each select="rel">
    		<p><b>Related Words: </b><a style="color:red;"><xsl:value-of select="."/></a></p>
    		</xsl:for-each>
    		<xsl:for-each select="near">
    		<p><b>Near Antonyms: </b><a style="color:red;"><xsl:value-of select="."/></a></p>
    		</xsl:for-each>
    		<xsl:for-each select="ant">
    		<p><b>Antonyms: </b><a style="color:red;"><xsl:value-of select="."/></a></p>
    		</xsl:for-each>
    	</xsl:for-each>
    </ol>
    </xsl:for-each>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>