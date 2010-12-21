@echo off
rem ontospread-test.bat
rem ontospread-test.sh

set JARS=activation.jar;antlr.jar;arq.jar;aterm.jar;commons-logging.jar;concurrent.jar;icu4j.jar;iri.jar;jaxb-api.jar;jaxb-impl.jar;jaxb-libs.jar;jena.jar;jsr173_api.jar;junit.jar;log4j.jar;ontospread.jar;ontospread-test-0.1-SNAPSHOT.jar;oro.jar;pellet.jar;stax.jar;stax-api.jar;xalan.jar;xercesImpl.jar;xml-apis.jar
set MAIN_CLASS=org.ontospread.tester.OntoSpreadTestRunner

set MAX_MEMORY=-Xmx100M 
set OPTIONS= %MAX_MEMORY%

%JAVA_HOME%\bin\java %OPTIONS% -cp %JARS% %MAIN_CLASS% %1