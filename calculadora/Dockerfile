# Usar una imagen base oficial de Node.js
FROM node:22.16
 
# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /usr/src/app
 
# Copiar los archivos de la aplicación al contenedor
COPY package*.json ./
 
# Instalar las dependencias
RUN npm install
 
# Copiar los archivos dist, package.json y package-lock.json al contenedor
COPY dist ./
 
# Exponer el puerto en el que corre la aplicación
EXPOSE 3000
 
# Comando para ejecutar la aplicación
CMD ["node", "main.js"]