# Entorno Desarrollo Solucionar Posibles Problemas

Si nos encontramos en la casuistica de realizar un 

```bash
mvn clean install
```
 (¡¡OJO!! siempre debemos de hacerlo primero al core y luego a parent)

y acto seguido al intentar desplegar nuestro .war en el servidor wildfly nos aparececiese un error similiar al siguiente

```log
java.lang.NoClassDefFoundError: com/ctc/wstx/io****
```

Tendremos que:

- Irnos a la carpeta del servidor wildfly donde esta desplegada nuestra aplicación "C:\Sevidores\wildfly-11.0.0.Final\standalone\deployments\"
- Entrar en la carpeta de nuestro .war "siga-web-1.1.0-SNAPSHOT.war\WEB-INF", abrir el archivo llamado jboss-deployment-structure.xml y degarlo de esta forma


```xml
<jboss-deployment-structure>
    <deployment>
        <!-- Exclusions allow you to prevent the server from automatically adding some dependencies     -->
        <exclusions>
            <module name="org.slf4j" />
            <module name="org.slf4j.impl" />
        </exclusions>
        <dependencies>
            <module name="org.codehaus.woodstox" />
        </dependencies>
    </deployment>
</jboss-deployment-structure>
```

Una vez realizado este paso, mirar dentro de la carpeta "/lib" del mismo directorio y comprobar que el siga-core***snapchot.jar este en formato .jar y no en formato carpeta. Si esto es así, tendremos eliminar la caperta ir a la carpeta target del proyecto siga-core, copiar el .jar que encontremos allí y moverlo a la carpeta /lib del deployments 

Una vez realizado los pasos anteriores podremos arrancar el proyecto y no debería de aparecernos ningún problema.