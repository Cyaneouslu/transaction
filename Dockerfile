# 使用官方的OpenJDK镜像作为基础镜像
FROM openjdk:21-jdk-oraclelinux8

# 设置工作目录
WORKDIR /app

# 将pom.xml和源代码复制到镜像中
COPY pom.xml .
COPY src ./src

# 打包应用程序
RUN mvn clean package -DskipTests

# 暴露应用程序运行所需的端口
EXPOSE 8080

# 将打包好的jar文件添加到镜像中
ADD target/transaction-0.0.1-SNAPSHOT.jar /app/transaction-0.0.1-SNAPSHOT.jar

# 定义启动命令
ENTRYPOINT ["java", "-jar", "/app/your-application-name.jar"]