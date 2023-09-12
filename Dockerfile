FROM openjdk:17-jdk-alpine

# Sao chép các tệp tin và thư mục cần thiết vào hình ảnh Docker
COPY src /app/src
WORKDIR /app

# Biên dịch ứng dụng
RUN javac src/server/Server.java

# Mở cổng mà server của bạn sẽ lắng nghe
EXPOSE 2301

# Chạy server khi container được khởi chạy
CMD ["java", "server.Server"]