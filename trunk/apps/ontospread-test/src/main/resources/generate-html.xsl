<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns="http://ontospread.sf.net" xmlns:rep="http://ontospread.sf.net">
    <xsl:output omit-xml-declaration="yes" method="html"/>
    <xsl:template match="rep:report-battery ">
        <html>
            <head><title>OntoSpread Report</title></head>
            <body>
                <xsl:apply-templates select="//rep:test-report"/>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="rep:test-report">
        
        <table>
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Métricas globales</xsl:with-param>
            <xsl:with-param name="col1-caption">Tipo</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
        </xsl:call-template>
        <tbody>
        <tr>
            <th>Total de conceptos iniciales</th><td><xsl:value-of select="count(//rep:scoredConcepts/rep:scoredConceptTO)"/></td>
        </tr>
            <tr>
                <th>Total de conceptos propagados </th><td><xsl:value-of select="count(//rep:spreadedConcepts/rep:spreadedConcept)"/></td>
        </tr>
            <tr>
                <th>Total de conceptos activados</th><td><xsl:value-of select="count(//rep:concepts/rep:entryConceptTO)"/></td>
        </tr>
            <tr>
                <th>Valor de activación más alto</th><td><xsl:call-template name="max"><xsl:with-param name="nodes" select="//rep:score"></xsl:with-param></xsl:call-template></td>
        </tr>
            <tr>
                <th> Camino de propagación más profundo</th><td><xsl:call-template name="max"><xsl:with-param name="nodes" select="//rep:depth"></xsl:with-param></xsl:call-template></td>
        </tr>
            <tr>
                <th>Tiempo de propagación</th><td><xsl:value-of select="//rep:spreadTime"/></td>
        </tr>
            <tr>
                <th>Función de activación </th><td><xsl:value-of select="//rep:function"/></td>
        </tr>
            <tr>
                <th>Recompensa</th><td><xsl:value-of select="//rep:prizePaths"/></td>
        </tr>
            <tr>
                <th>Ontologías</th><td><ul><xsl:apply-templates select="rep:resources"/></ul></td>
            </tr>
            <tr>
                <th>Relaciones</th><td><ul><xsl:apply-templates select="./rep:ontoSpreadConfig/rep:relations"/></ul></td>
            </tr>
        </tbody>
        </table>    
        
       
        <!--
        \begin{longtable}{|c|c|}
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Conceptos Activados</xsl:with-param>
            <xsl:with-param name="col1-caption">URI</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
        </xsl:call-template>
        \end{longtable}      
        -->
        <table>
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Conceptos Propagados</xsl:with-param>
            <xsl:with-param name="col1-caption">URI</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>                
        </xsl:call-template>
            <tbody>
        <xsl:apply-templates select="//rep:finalSpreadedConcepts/rep:spreadedConceptTO" mode="spread">
            <xsl:sort select="rep:score" order="descending" data-type="number"/>
        </xsl:apply-templates>
                </tbody>
         
        </table>     
    
        <table>
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Conceptos Finales</xsl:with-param>
            <xsl:with-param name="col1-caption">URI</xsl:with-param>
            <xsl:with-param name="col2-caption">Valor</xsl:with-param>
        </xsl:call-template>
         <tbody>
        <xsl:apply-templates select="//rep:finalSpreadedConcepts/rep:spreadedConceptTO">
            <xsl:sort select="rep:score" order="descending" data-type="number"/>
        </xsl:apply-templates> 
           
                </tbody>
            <tfoot>
                <tr>
                    <th scope="row">Total</th>
                    <td colspan="4"><xsl:value-of select="count(//rep:finalSpreadedConcepts/rep:spreadedConceptTO)"/></td>
                </tr>
            </tfoot>
        </table>      
         

        <table>
        <xsl:call-template name="table-skeleton">
            <xsl:with-param name="caption">Información ontología</xsl:with-param>
            <xsl:with-param name="col1-caption">Tipo</xsl:with-param>
            <xsl:with-param name="col2-caption">Número</xsl:with-param>                
        </xsl:call-template>
            <tbody>
                <xsl:apply-templates select="//rep:statTO"></xsl:apply-templates>
            </tbody>
            <tfoot>
                <tr>
                    <th scope="row">Total</th>
                    <td colspan="4"><xsl:value-of select="count(//rep:statTO)"/></td>
                </tr>
            </tfoot>
        </table>      
   
    </xsl:template>
    <xsl:template match="rep:relations">
        <ul><xsl:apply-templates select="rep:location"/></ul>
    </xsl:template>
    <xsl:template match="rep:resources">
        <ul><xsl:apply-templates select="rep:location"/></ul>
    </xsl:template>
    <xsl:template match="rep:location">
        <li><xsl:value-of select="."/></li>
    </xsl:template>
    <xsl:template match="rep:function">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:activation">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:minConcepts">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:maxConcepts">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:time">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:context">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:retries">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    <xsl:template match="rep:prizePaths">
        <th><xsl:value-of select="name(.)"/> </th><td><xsl:value-of select="."/></td>
    </xsl:template>
    
    <xsl:template match="rep:statTO">
        <!--<xsl:value-of select="substring-after(./rep:type,'#')"/> -->
        <tr>
            <th>         
                <xsl:choose>
                    <xsl:when test="substring-after(./rep:type,'#')!=''">
                        <xsl:value-of select="substring-after(./rep:type,'#')"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="./rep:type"/>
                    </xsl:otherwise>
                </xsl:choose>   
             </th><td> <xsl:value-of select="./rep:value"/></td>
        </tr>
    </xsl:template>
    
    <xsl:template match="rep:spreadedConceptTO" mode="spread">
        <xsl:variable name="uri" select="string(./rep:conceptUri)"/>
        <xsl:if test="//rep:spreadedConcept[string(.)=string($uri)]">        
           <tr>
               <th><xsl:value-of select="substring-after($uri,'#')"/> </th><td><xsl:value-of select="./rep:score"/></td>
           </tr>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="rep:spreadedConceptTO">
        <xsl:variable name="uri" select="string(./rep:conceptUri)"/>
        <tr>
            <th><xsl:value-of select="substring-after($uri,'#')"/></th><td><xsl:value-of select="./rep:score"/></td>
        </tr>
    </xsl:template>
    
    <xsl:template name="table-skeleton">
        <xsl:param name="caption"></xsl:param>
        <xsl:param name="col1-caption"></xsl:param>
        <xsl:param name="col2-caption"></xsl:param>
        <caption><xsl:value-of select="$caption"/></caption>
            <thead>
                <tr>
                    <th scope="col"><xsl:value-of select="$col1-caption"/></th>
                    <th scope="col"><xsl:value-of select="$col2-caption"/></th>
                </tr>
            </thead>
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
