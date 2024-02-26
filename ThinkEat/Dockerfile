# 使用 OpenJDK 17 作為基本映像
FROM openjdk:17

# 設置工作目錄
WORKDIR /app

# 複製 JAR 檔案到容器中的工作目錄
COPY target/ThinkEat.jar /app/ThinkEat.jar

# 暴露應用程式的端口
EXPOSE 9990

# 定義啟動命令
CMD ["java", "-jar", "ThinkEat.jar"]