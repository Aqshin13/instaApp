# instaApp

An Instagram-like social media application built with Java and Spring Boot.  

## 🚀 Features

- Backend built with Java 17 and Spring Boot
- Docker and Docker Compose support
- Basic social media features (post sharing, user management,notification etc.)

> **Note:** Some features are still under development and may not be fully functional yet, such as:
> - Commenting on posts
> - Liking posts and comments
> - .etc

## 🛠️ Installation

### Requirements

- Java 17
- Maven 3.8+
- Docker and Docker Compose

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/Aqshin13/instaApp.git
   cd instaApp

2. Start the application using Docker Compose:
    ```bash
    docker-compose up --build
3. The application will be running at http://localhost:8080 by default.



## 🧱 Project Structure

    instaApp/
    ├── .mvn/                  # Maven Wrapper files
    ├── src/main/              # Application source code
    │   ├── java/              # Java source files
    │   └── resources/         # Application configurations
    ├── Dockerfile             # For building Docker image
    ├── docker-compose.yml     # Docker Compose service definition
    ├── pom.xml                # Maven configuration
    └── mvnw / mvnw.cmd        # Maven Wrapper scripts


## 📦 Technologies Used
    Java 17
    
    Spring Boot
    
    Maven
    
    Docker
    
    Docker Compose
