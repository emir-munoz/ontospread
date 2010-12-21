@echo off


set JARS_FROM_TEST=activation.jar;antlr.jar;arq.jar;aterm.jar;commons-logging.jar;concurrent.jar;icu4j.jar;iri.jar;jaxb-api.jar;jaxb-impl.jar;jaxb-libs.jar;jena.jar;jsr173_api.jar;oro.jar;pellet.jar;stax.jar;stax-api.jar;xalan.jar;xercesImpl.jar;xml-apis.jar
set JARS_GUI=ontospread-gui-0.1-SNAPSHOT.jar;baseline.jar;jpowergraph-0.2-common.jar;jpowergraph-0.2-swt.jar;junit-3.8.1.jar;log4j-1.2.14.jar;spring.jar;spring-beans.jar;spring-core.jar;swt-grouplayout.jar;ontospread-0.1-SNAPSHOT.jar
set JARS_SWT=org.eclipse.core.commands_3.2.0.I20060605-1400.jar;org.eclipse.core.runtime_3.2.0.v20060603.jar;org.eclipse.equinox.common_3.2.0.v20060603.jar;org.eclipse.equinox.registry_3.2.0.v20060601.jar;org.eclipse.jface.text_3.2.0.v20060605-1400.jar;org.eclipse.jface_3.2.0.I20060605-1400.jar;org.eclipse.osgi_3.2.0.v20060601.jar;org.eclipse.swt.win32.win32.x86_3.2.0.v3232m.jar;org.eclipse.text_3.2.0.v20060605-1400.jar;org.eclipse.ui.forms_3.2.0.v20060602.jar;org.eclipse.ui.workbench_3.2.0.I20060605-1400.jar
set JARS_NATIVE=swt.jar;swt-debug.jar
set MAIN_CLASS=org.ontospread.gui.view.OntoSpreadViewerMain


set MAX_MEMORY=
set OPTIONS= %MAX_MEMORY%

rem put this path -Djava.library.path={runtime-library-path}
rem with this files: swt-win32-3232.dll, swt-awt-win32-3232.dll, swt-gdip-win32-3232.dll, swt-wgl-win32-3232.dll
rem you can find inside eclipse home dir: search *.dll
rem put this native files jre/bin
rem reference http://www.eclipse.org/swt/faq.php#missingdll

%JAVA_HOME%\bin\java %OPTIONS% -cp %JARS_FROM_TEST%;%JARS_GUI%;%JARS_SWT%;%JARS_NATIVE% %MAIN_CLASS% %1

