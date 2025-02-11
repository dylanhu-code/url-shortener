# URL Shortener

## 📌 Description
A lightweight URL shortening service built with Spring Boot. It allows users to shorten long URLs and ensures efficient performance with caching and rate limiting. The service is **fully containerized** and **deployed on AWS** for scalability.

## 🚀 Features
- **Shorten URLs**: Converts long URLs into short, easy-to-share links.
- **Caching**: Uses **Redis** to speed up lookups and reduce database load.
- **Rate Limiting**: Implements **Bucket4J** to prevent abuse and excessive API calls.
- **Dockerized**: Fully containerized with **Docker & Docker Compose** for easy deployment.
- **AWS Deployment**: Hosted on an **EC2 instance** with an **RDS PostgreSQL** database.

---

## ⚙️ Getting Started

### **📌 Prerequisites**
Ensure you have the following installed before running the application:
- **Java 21**
- **Maven 3.8.6+**
- **Docker & Docker Compose**
- **PostgreSQL** (if not using AWS RDS)
- **Redis** (if not using Docker for Redis)

---

### **🛠️ Local Setup**
1. **Clone the repository**  
   ```bash
   git clone https://github.com/dylanhu-code/url-shortener.git
   cd url-shortener
   ```
   
2. Fill the variables in the .env file in the root directory with the corresponding values:  
   BASE_URL=http://localhost:8080/  
   DB_URL=jdbc:postgresql://localhost:5432/your_db_name  
   DB_USER=your_db_user  
   DB_PASS=your_db_password  

3. Build and run the app using Docker Compose:
   ```bash
   docker-compose up --build
   ```

4. Access the service:
- Local: http://localhost:8080
- Deployed (AWS EC2): http://your-ec2-ip/shorten
   
### **☁️ AWS deployment**
For AWS deployment, follow these steps:
1. Launch an EC2 instance (Ubuntu recommended) and SSH into it:
   ```bash
   ssh -i your-key.pem ubuntu@your-ec2-ip
   ```
2. Install Docker & Docker Compose on EC2:
   ```bash
   sudo apt update && sudo apt install docker.io -y
   sudo systemctl enable docker
   sudo apt install docker-compose -y
   ```
4. Set up an RDS PostgreSQL instance and configure the connection string in your .env file.
5. Deploy the Docker container on EC2:
   ```bash
   git clone https://github.com/dylanhu-code/url-shortener.git
   cd url-shortener
   docker-compose up --build -d
   ```


## **🛠️Technologies Used**
- **Backend:** Java 21, Spring Boot, Spring Web (REST API), Spring Data JPA
- **Frontend:** HTML, CSS, Thymeleaf (for dynamic content rendering)
- **Database:** PostgreSQL (AWS RDS and EC2)
- **Caching:** Redis
- **Rate Limiting:** Bucket4J
- **Containerization:** Docker & Docker Compose
- **Environment Variables Management:** dotenv-java


## **📜License**
This project is licensed under the MIT License - see the LICENSE.md file for details.
