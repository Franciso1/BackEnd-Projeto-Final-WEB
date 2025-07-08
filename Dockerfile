# Estágio 1: Build com Maven (O "Construtor")
# Usa uma imagem oficial que já vem com Maven e Java para compilar seu projeto.
FROM maven:3.8.5-openjdk-17 AS build

# Cria um diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia todo o seu código para dentro do contêiner.
COPY . .

# Roda o comando para gerar o arquivo .jar, pulando os testes para acelerar.
RUN mvn clean package -DskipTests

# --- (Fim do primeiro estágio) ---

# Estágio 2: Execução (A Imagem Final e Leve)
# Começa com uma imagem Java "slim" (magra), que é muito menor.
FROM openjdk:17-jdk-slim

# Cria um diretório de trabalho.
WORKDIR /app

# Copia APENAS o arquivo .jar gerado no estágio anterior.
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que sua aplicação usa (conforme seu application.properties).
EXPOSE 8080

# Comando final que será executado quando o contêiner iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]