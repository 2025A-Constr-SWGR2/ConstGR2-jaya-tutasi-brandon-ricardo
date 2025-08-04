# InventarioCRUD

Aplicación web Java tipo CRUD construida con servlets, JSP, Jakarta EE y Maven. Se puede ejecutar mediante Docker y cuenta con flujos de integración continua definidos en GitHub Actions.

---

## 🚀 Cómo ejecutar el proyecto con Docker

### 1. 📦 Construir el `.war` (opcional si usas IntelliJ)

Si usas Maven directamente:

```bash
mvn clean package
El archivo Inventario.war se generará en la carpeta target/.

2. 🐳 Construir la imagen Docker
Desde la raíz del proyecto (donde está el Dockerfile):

bash
Copiar
Editar
docker build -t inventario-app .
3. ▶️ Ejecutar el contenedor
bash
Copiar
Editar
docker run -d -p 8080:8080 --name inventario inventario-app
Si el puerto 8080 ya está en uso, puedes mapearlo a otro:

bash
Copiar
Editar
docker run -d -p 8081:8080 --name inventario inventario-app
4. 🌐 Acceder a la aplicación
Abre tu navegador en:

arduino
Copiar
Editar
http://localhost:8080

⚙️ Flujos de trabajo (GitHub Actions)
El proyecto incluye los siguientes workflows automáticos en .github/workflows/:

🛠️ Compilación del Proyecto
Compila el código con Maven en cada push.
Utiliza JDK 17 y guarda los archivos .class como artefactos temporales.

yaml
Copiar
Editar
name: Compilación del Proyecto
🔍 Análisis de Calidad de Código
Ejecuta herramientas de análisis estático como:

Checkstyle (estilo)

PMD (malas prácticas)

SpotBugs (defectos de ejecución)

Sube los reportes generados como artefactos.

yaml
Copiar
Editar
name: Análisis de Calidad de Código
🧪 Pruebas Unitarias
Ejecuta las pruebas con H2 como base de datos embebida, usando JUnit y Jacoco para reportes de cobertura.

yaml
Copiar
Editar
name: Pruebas Unitarias
⚙️ Node build
Compila una aplicación adicional ubicada en la carpeta calculadora/ usando Node.js 22.

yaml
Copiar
Editar
name: Node build

📁 Estructura esperada
bash
Copiar
Editar
InventarioCRUD/
├── src/
├── target/                  # Aquí se genera el WAR
├── Dockerfile
├── pom.xml
├── .github/
│   └── workflows/           # Archivos YAML de GitHub Actions
├── out/artifacts/          # Si usas IntelliJ para generar el WAR
✅ Requisitos
Java 17 (JDK)

Maven

Docker

(Opcional) IntelliJ IDEA configurado con Facet Web

📦 Dockerfile de ejemplo
Dockerfile
Copiar
Editar
FROM tomcat:9.0-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/Inventario.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
🧾 Licencia
Este proyecto es educativo y no tiene fines comerciales por tal razon tiene funciones basicas