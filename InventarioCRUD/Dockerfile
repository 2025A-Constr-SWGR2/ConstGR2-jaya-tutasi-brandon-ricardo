# Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM tomcat:10.1.18-jdk17-temurin-jammy

# 1. Configurar H2 con persistencia
ENV DB_URL="jdbc:h2:file:/usr/local/tomcat/h2-data/inventariodb;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE"
ENV DB_USER="sa"
ENV DB_PASSWORD=""

# 2. Copiar el WAR con tu contexto específico
COPY --from=build /app/target/Inventario.war /usr/local/tomcat/webapps/ROOT.war

# 3. Crear directorio para datos H2 y establecer permisos
RUN mkdir -p /usr/local/tomcat/h2-data && \
    chmod -R 777 /usr/local/tomcat/h2-data

EXPOSE 8080

CMD catalina.sh run