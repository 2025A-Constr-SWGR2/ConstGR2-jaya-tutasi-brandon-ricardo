# Usa imagen de Tomcat con Java 17
FROM tomcat:10.0-jdk17

# Elimina las apps por defecto (como manager, host-manager, etc.)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia tu WAR renombrado como ROOT.war para que cargue directamente en /
COPY target/Inventario.war /usr/local/tomcat/webapps/ROOT.war

# Expón el puerto 8080
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
