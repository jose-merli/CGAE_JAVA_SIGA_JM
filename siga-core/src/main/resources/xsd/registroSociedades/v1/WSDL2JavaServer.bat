set URL_WSDL=./RegistroSociedadesServer.wsdl

set PAQUETE=org.itcgae.sspp.ws.registroSociedades
set AXIS2_HOME=C:\Program Files\axis2-1.6.2
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_231

"%AXIS2_HOME%\bin\WSDL2Java" -uri %URL_WSDL% -p %PAQUETE% -d xmlbeans -S java -o tmp/registroSociedades -sp -ssi -ss -sd -g -ns2p http://www.w3.org/2004/06/xmlmime=%PAQUETE%.wsdl.xmlmime,http://schemas.xmlsoap.org/soap/encoding/=%PAQUETE%.wsdl.soap.encoding