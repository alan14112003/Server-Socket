FROM openjdk:17-jdk-alpine

# Sao chép các tệp tin và thư mục cần thiết vào hình ảnh Docker
COPY . /app
WORKDIR /app

# Biên dịch ứng dụng
RUN javac ServerParallel.java

# Mở cổng mà server của bạn sẽ lắng nghe
EXPOSE 10000

# Chạy server khi container được khởi chạy
CMD ["java", "ServerParallel"]