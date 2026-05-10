INSTRUCCIONES - carpeta lib/

Este archivo indica cómo obtener el conector MySQL si lo deseas localmente.

Nota: Como este es un proyecto Maven, no es necesario añadir manualmente el JAR de MySQL en la carpeta lib/
porque Maven descargará y gestionará la dependencia indicada en pom.xml (cuando ejecutes `mvn package` o lo importes en NetBeans).

Si aún así quieres añadir el JAR manualmente:
1. Descarga el conector desde: https://dev.mysql.com/downloads/connector/j/
2. Copia el archivo JAR (por ejemplo: mysql-connector-j-8.4.0.jar) dentro de la carpeta `lib/`.
3. Si lo añades manualmente, NetBeans lo detectará si lo agregas al Classpath del proyecto (no es necesario para Maven).

