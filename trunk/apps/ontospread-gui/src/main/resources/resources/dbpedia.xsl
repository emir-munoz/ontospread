<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:units="http://dbpedia.org/units/"
    xmlns:foaf="http://xmlns.com/foaf/0.1/"
    xmlns:geonames="http://www.geonames.org/ontology#"
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
    xmlns:owl="http://www.w3.org/2002/07/owl#"
    xmlns:p="http://dbpedia.org/property/"
    xmlns:dcterms="http://purl.org/dc/terms/"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
    xmlns:yago="http://dbpedia.org/class/yago/"
    xmlns:dbpedia="http://dbpedia.org/resource/"
    xmlns:skos="http://www.w3.org/2004/02/skos/core#"
    xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    xmlns="http://ontospread.sf.net">
    <xsl:output method="xml" encoding="UTF-8" version="1.0"/>

	<xsl:param name="uri">http://dbpedia.org/resource/Oviedo</xsl:param>
    <xsl:template match="/">
		<xsl:element name="concept">
			<xsl:element name="conceptDescription">
				<xsl:element name="uri"><xsl:value-of select="$uri"/></xsl:element>
				<xsl:element name="name"><xsl:value-of select="substring-after($uri,'http://dbpedia.org/resource/')"/></xsl:element>
				<xsl:element name="description"><xsl:value-of select="substring-after($uri,'http://dbpedia.org/resource/')"/></xsl:element>
				<xsl:element name="type">instance</xsl:element>
			</xsl:element>
		<xsl:element name="relations">
			<xsl:apply-templates select="//rdf:Description"/>
		</xsl:element>
		<xsl:element name="instances"/>
		<xsl:element name="instanceof"/>
		</xsl:element>
    </xsl:template>

    <xsl:template match="rdf:Description">
		<xsl:element name="relation">
			<xsl:element name="conceptDescription">
				<xsl:element name="uri"><xsl:value-of select="@rdf:about"/></xsl:element>
				<xsl:element name="name"><xsl:value-of select="substring-after(@rdf:about,'http://dbpedia.org/resource/')"/></xsl:element>
				<xsl:element name="description"><xsl:value-of select="substring-after(@rdf:about,'http://dbpedia.org/resource/')"/></xsl:element>
				<xsl:element name="type">instance</xsl:element>
			</xsl:element>
			<xsl:element name="uri"><xsl:value-of select="namespace-uri(./*)"/><xsl:value-of select="local-name(./*)"/></xsl:element>
			<xsl:element name="onproperty"><xsl:value-of select="namespace-uri(./*)"/><xsl:value-of select="local-name(./*)"/></xsl:element>
			<xsl:element name="hierarchy">subclass</xsl:element>
			<xsl:element name="value">FIXME</xsl:element>
			<xsl:element name="description">FIXME</xsl:element>
		</xsl:element>
    </xsl:template>    
</xsl:stylesheet>
