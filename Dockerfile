# Imagem base com Java 21
FROM eclipse-temurin:21-jdk

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven
COPY target/*.jar app.jar

# Porta utilizada pela aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]