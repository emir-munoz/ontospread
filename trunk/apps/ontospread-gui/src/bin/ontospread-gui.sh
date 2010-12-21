#!/bin/bash

export JARS=activation.jar:antlr.jar:arq.jar:aterm.jar:commons-logging.jar:concurrent.jar:icu4j.jar:iri.jar:jaxb-api.jar:jaxb-impl.jar:jaxb-libs.jar:jena.jar:jsr173_api.jar:junit.jar:log4j.jar:ontospread.jar:ontospread-gui-0.1-SNAPSHOT.jar:oro.jar:pellet.jar:stax.jar:stax-api.jar:xalan.jar:xercesImpl.jar:xml-apis.jar
export MAIN_CLASS=org.ontospread.gui.view.OntoSpreadViewerMain

export MAX_MEMORY=-Xmx100M 
export OPTIONS= $MAX_MEMORY

$JAVA_HOME\bin\java $OPTIONS -cp $JARS $MAIN_CLASS $@
