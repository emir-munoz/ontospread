<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns="http://ontospread.sf.net" xmlns:rep="http://ontospread.sf.net">
    <xsl:output omit-xml-declaration="yes" method="text"/>
    <xsl:template match="rep:report-battery ">
        <xsl:apply-templates select="//rep:test-report"/>
    </xsl:template>
    
    <xsl:template match="rep:test-report">
        
        \begin{longtable}{|p{10cm}|p{2cm}|}
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Métricas globales</xsl:with-param>
            <xsl:with-param name="col1-caption">Tipo</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
        </xsl:call-template>
        Total de conceptos iniciales<xsl:text><![CDATA[&]]> </xsl:text>$<xsl:value-of select="count(//rep:scoredConcepts/rep:scoredConceptTO)"/>$ \\ \hline
        Total de conceptos propagados <xsl:text><![CDATA[&]]> </xsl:text>$<xsl:value-of select="count(//rep:spreadedConcepts/rep:spreadedConcept)"/>$ \\ \hline
        Total de conceptos activados <xsl:text><![CDATA[&]]></xsl:text> $<xsl:value-of select="count(//rep:concepts/rep:entryConceptTO)"/>$ \\ \hline
        Valor de activación más alto <xsl:text><![CDATA[&]]></xsl:text> $<xsl:call-template name="max"><xsl:with-param name="nodes" select="//rep:score"></xsl:with-param></xsl:call-template>$ \\ \hline
        Camino de propagación más profundo <xsl:text><![CDATA[&]]> </xsl:text> $<xsl:call-template name="max"><xsl:with-param name="nodes" select="//rep:depth"></xsl:with-param></xsl:call-template>$ \\ \hline
        Tiempo de propagación <xsl:text><![CDATA[&]]></xsl:text> $<xsl:value-of select="//rep:spreadTime"/>$\\ \hline
        Función de activación <xsl:text><![CDATA[&]]></xsl:text> <xsl:value-of select="//rep:function"/>\\ \hline
        Recompensa <xsl:text><![CDATA[&]]></xsl:text> <xsl:value-of select="//rep:prizePaths"/>\\ \hline
        \end{longtable}      
        
        \begin{longtable}{|p{10cm}|p{2cm}|}
            <xsl:call-template name="table-skeleton">
                <xsl:with-param name="caption">Restricciones</xsl:with-param>
                <xsl:with-param name="col1-caption">Tipo</xsl:with-param>
                <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
            </xsl:call-template>
        <xsl:apply-templates select="rep:ontoSpreadConfig"></xsl:apply-templates>
        \end{longtable}        
        <!--
        \begin{longtable}{|c|c|}
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Conceptos Activados</xsl:with-param>
            <xsl:with-param name="col1-caption">URI</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
        </xsl:call-template>
        \end{longtable}      
        -->
        \begin{longtable}{|p{10cm}|p{2cm}|}
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Conceptos Propagados</xsl:with-param>
            <xsl:with-param name="col1-caption">URI</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
        </xsl:call-template>
        <xsl:apply-templates select="//rep:finalSpreadedConcepts/rep:spreadedConceptTO" mode="spread">
            <xsl:sort select="rep:score" order="descending" data-type="number"/>
        </xsl:apply-templates>
        \end{longtable}      
    
         begin{longtable}{|c|c|}
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Conceptos Finales</xsl:with-param>
            <xsl:with-param name="col1-caption">URI</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>
        </xsl:call-template>
        <xsl:apply-templates select="//rep:finalSpreadedConcepts/rep:spreadedConceptTO">
            <xsl:sort select="rep:score" order="descending" data-type="number"/>
        </xsl:apply-templates> 
        \end{longtable}      
         

        \begin{longtable}{|c|c|}
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Información ontología</xsl:with-param>
            <xsl:with-param name="col1-caption">Tipo</xsl:with-param>
            <xsl:with-param name="col2-caption">Número</xsl:with-param>                
        </xsl:call-template>
        <xsl:apply-templates select="//rep:statTO"></xsl:apply-templates>
        \end{longtable}      
   
    </xsl:template>
    <xsl:template match="rep:activation">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    <xsl:template match="rep:minConcepts">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    <xsl:template match="rep:maxConcepts">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    <xsl:template match="rep:time">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    <xsl:template match="rep:context">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    <xsl:template match="rep:retries">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    <xsl:template match="rep:prizePaths">
        <xsl:value-of select="name(.)"/> <xsl:text><![CDATA[&]]></xsl:text><xsl:value-of select="."/> \\ \hline
    </xsl:template>
    
    <xsl:template match="rep:statTO">
        <!--<xsl:value-of select="substring-after(./rep:type,'#')"/> -->
        <xsl:value-of select="string(./rep:type)"/> <xsl:text><![CDATA[&]]></xsl:text> <xsl:value-of select="./rep:value"/> \\ \hline
    </xsl:template>
    
    <xsl:template match="rep:spreadedConceptTO" mode="spread">
        <xsl:variable name="uri" select="string(./rep:conceptUri)"/>
        <xsl:if test="//rep:spreadedConcept[string(.)=string($uri)]">        
        <xsl:value-of select="concat('','\#',substring-after(string(./rep:conceptUri),'#'))"/> <xsl:text><![CDATA[&]]></xsl:text>  $<xsl:value-of select="./rep:score"/>$ \\ \hline
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="rep:spreadedConceptTO">
        <xsl:variable name="uri" select="string(./rep:conceptUri)"/>
        <xsl:value-of select="concat('','\#',substring-after(string(./rep:conceptUri),'#'))"/> <xsl:text><![CDATA[&]]></xsl:text>  $<xsl:value-of select="./rep:score"/>$ \\ \hline
    </xsl:template>
    
    <xsl:template name="table-skeleton">
        <xsl:param name="caption"></xsl:param>
        <xsl:param name="col1-caption"></xsl:param>
        <xsl:param name="col2-caption"></xsl:param>
        \caption{<xsl:value-of select="$caption"/>}\\
        \hline\hline
        \multicolumn{2}{|c|}{\textbf{<xsl:value-of select="$caption"/>}}\\
        \hline\hline
        \textit{<xsl:value-of select="$col1-caption"/>} <xsl:text><![CDATA[&]]></xsl:text>  \textit{<xsl:value-of select="$col2-caption"/>} \\
        \hline
        \endfirsthead
        \caption[]{<xsl:value-of select="$caption"/> (continuación)}\\
        \hline\hline
        \multicolumn{2}{|c|}{\textbf{<xsl:value-of select="$caption"/>}}\\
        \hline \hline
        \textit{<xsl:value-of select="$col1-caption"/>} <xsl:text><![CDATA[&]]></xsl:text>  \textit{<xsl:value-of select="$col2-caption"/>} \\
        \hline
        \endhead
        \hline
        \multicolumn{2}{|c|}{Sigue $\ldots$}\\
        \hline
        \endfoot
        \hline
        \hline
        \endlastfoot
    </xsl:template>
    
    <xsl:template name="max">
        <xsl:param name="nodes"
            select="/.." />
        <xsl:choose>
            <xsl:when test="not($nodes)">NaN</xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="$nodes">
                    <xsl:sort data-type="number"
                        order="descending" />
                    <xsl:if test="position() = 1">
                        <xsl:value-of select="number(.)" />
                    </xsl:if>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
</xsl:stylesheet>
