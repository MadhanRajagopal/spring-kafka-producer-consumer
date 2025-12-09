# spring-kafka-producer-consumer

## 🚀 Prerequisites

Make sure you have the following installed:

- **Docker**   
- **Java 17+**
- **Maven/Gradle** 


# Kafka (KRaft Mode) + Kafdrop using Docker Compose 🚀

This project provides a lightweight Kafka setup using **KRaft mode (no Zookeeper)** along with **Kafdrop**, a web UI for exploring Kafka topics, consumers, and messages.

This setup is ideal for local development and testing Spring Boot Kafka applications.

---

## 📌 Services Included

### ** Kafka (Confluent Kafka – KRaft mode)**  
- Runs as a single-node KRaft controller + broker  
- Exposed on port **9092**  
- No Zookeeper required  

---
### Run Kafka Server using 🐳 Docker Compose

#### Start container 

```sh
docker-compose -f docker-compose-kafka.yml up
```

#### Stop container 

```sh
docker-compose -f docker-compose-kafka.yml down
```
---
### ▶️ Running Producer & Consumer Applications

1️⃣ Build the project

Go inside each project:
```
cd spring-boot-kafka-producer
./mvnw clean package
```

```
cd spring-boot-kafka-consumer
./mvnw clean package
```

2️⃣ Run the application

#### Producer:
```
./mvnw spring-boot:run
```

#### Consumer:

```
./mvnw spring-boot:run
```
