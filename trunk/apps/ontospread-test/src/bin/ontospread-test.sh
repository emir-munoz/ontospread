#Creamos las variables necesarias para guardar los path de los directorios de la aplicación.
_JAVACMD=$JAVA_HOME/bin/java
if [ "${JAVA_HOME}" = "" ] ; then
echo "Warning: JAVA_HOME no encontrado."
_JAVACMD=java
fi

JAVA_OPTS=-classpath

_CLASSPATH="activation.jar:antlr.jar:arq.jar:aterm.jar:commons-logging.jar:concurrent.jar:icu4j.jar:iri.jar:jaxb-api.jar:jaxb-impl.jar:jaxb-libs.jar:jena.jar:jsr173_api.jar:junit.jar:log4j.jar:ontospread.jar:ontospread-test.jar:ontospread-test-0.1-SNAPSHOT.jar:oro.jar:pellet.jar:stax.jar:stax-api.jar:xalan.jar:xercesImpl.jar:xml-apis.jar"

"${_JAVACMD}" ${JAVA_OPTS} ${_CLASSPATH} org.ontospread.tester.OntoSpreadTestRunner "$@"
