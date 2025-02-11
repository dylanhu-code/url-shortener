# URL Shortener
**Description**
A simple URL shortening service built with Spring Boot, Redis, PostgreSQL (AWS RDS), Docker, and rate limiting using Bucket4J.

## Features
- **Shorten URLs**: Converts long URLs into short URLs.
- **Expiry Date**: URLs can be set to expire after a certain period.
- **Caching**: Redis is used for caching to improve performance.
- **Rate Limiting**: Implemented using Bucket4J to prevent abuse of the service.
- **Dockerized**: The app is fully containerized using Docker and Docker Compose.
- **AWS Deployment**: Hosted on an EC2 instance with an RDS PostgreSQL database.

## Getting Started

### Prerequisites
- Docker
- Docker Compose
- Java 21
- PostgreSQL (if not using AWS RDS)
- Redis (if not using Docker for Redis)
- Maven

### Local Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/dylanhu-code/url-shortener.git
   cd url-shortener-app
   ```
   
2. Create a .env file in the root directory with the following variables:
   BASE_URL=http://localhost:8080/
   DB_URL=jdbc:postgresql://localhost:5432/your_db_name
   DB_USER=your_db_user
   DB_PASS=your_db_password

3. Build and run the app using Docker Compose:
4. ```bash
   docker-compose up --build
   ```
### AWS deployment
For AWS deployment, follow these steps:
1. Set up an EC2 instance with Docker installed.
2. Set up an RDS PostgreSQL instance and configure the connection string in your .env file.
3. Deploy the Docker containers to EC2.

## Technologies Used
- Java 21
- Maven
- Spring Boot
- Redis (Caching)
- PostgreSQL (AWS RDS)
- Docker & Docker Compose
- Bucket4J (Rate Limiting)

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.