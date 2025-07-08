# --- ESTÁGIO 1: Build com Maven e Alpine ---
# Usamos uma imagem oficial Temurin (OpenJDK) baseada em Alpine, que é leve.
# Esta imagem já contém o Maven e o JDK necessários para compilar seu projeto.
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia todo o seu código para o diretório de trabalho.
COPY . .

# Roda o comando do Maven para gerar o arquivo .jar executável.
RUN mvn clean package -DskipTests

# --- ESTÁGIO 2: A Imagem Final (LEVE com Alpine) ---
# Começamos com uma imagem oficial Temurin JRE baseada em Alpine.
# JRE (Java Runtime Environment) é menor que o JDK, pois contém apenas
# o necessário para EXECUTAR, não para compilar.
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho.
WORKDIR /app

# Copia APENAS o arquivo .jar gerado no estágio anterior.
COPY --from=build /app/target/*.jar app.jar

# "Avisa" ao Docker que sua aplicação, dentro do contêiner, usa a porta 8080.
EXPOSE 8080

# O comando que será executado quando o contêiner iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]