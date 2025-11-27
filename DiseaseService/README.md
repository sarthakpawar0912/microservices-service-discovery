# 🏥 Microservices Service Discovery System  
A complete microservices ecosystem built using **Spring Boot**, **Spring Cloud**, **Eureka Server**, **Eureka Clients**, and **RestTemplate/OpenFeign**, demonstrating dynamic service discovery, communication, load balancing, and distributed architecture.

---

# 🚀 Project Overview  
This repository contains **multiple microservices** working together in a distributed system:

MICROSERVICE PROJECTS / SERVICE DISCOVERY
│
├── Eureka-Server
├── DocterService
├── DoctorPortal
├── PatientService
└── DiseaseService (optional)


This project demonstrates:

- Dynamic Service Discovery  
- Auto Registration to Eureka  
- Client-side Load Balancing  
- Inter-service Communication  
- REST API based microservices  
- Clean, Product-level architecture  

---

# 🌐 1. Architecture Diagram  

                +-----------------------+
                |     Doctor Portal     |
                | (Eureka Client + API) |
                +-----------+-----------+
                            |
                            | RestTemplate / Feign
                            |
+-------------------+ +---v--------------------+
| PatientService | | DocterService |
| (Eureka Client) | | (Eureka Client) |
+-------------------+ +------------------------+
\ /
\ /
\ /
\ /
\ /
+----------------+
| Eureka Server |
+----------------+

---

# 🔎 2. What is Service Discovery?

Microservices do **not** run on fixed ports.  
Example:

- Today `DocterService` runs on **8082**
- Tomorrow maybe **8084**
- In production: 10+ dynamic instances

Problems without service discovery:

❌ Cannot hardcode URLs  
❌ Cannot know instance health  
❌ Cannot load balance  
❌ Cannot scale horizontally  

### ✔ Solution → **Service Discovery**

Service Discovery = *address book of microservices*

It tells you:

- Which services are running  
- Their IP + Port  
- Their health status  
- Number of instances  
- Which instance is free  
- Auto-Load balancing  

### Service Discovery Types:

| Type | Description | Example |
|------|-------------|---------|
| **1. Client-Side Discovery** | Client finds service instance from registry | Eureka |
| **2. Server-Side Discovery** | Load balancer chooses instance | AWS ELB |
| **3. DNS-Based Discovery** | Instances registered via DNS | Kubernetes |
| **4. Sidecar Discovery** | Service mesh proxy handles discovery | Istio + Envoy |

Our project uses:

👉 **Netflix Eureka – Client-Side Service Discovery**

---

# 📡 3. Eureka Server

Eureka Server is the **registry center**.

### Responsibilities:
- Store list of available services  
- Update health of services  
- Provide lookup for clients  
- Enable load balancing  
- Handle auto registration / de-registration  

### Eureka Server URL:


---

# 🔎 2. What is Service Discovery?

Microservices do **not** run on fixed ports.  
Example:

- Today `DocterService` runs on **8082**
- Tomorrow maybe **8084**
- In production: 10+ dynamic instances

Problems without service discovery:

❌ Cannot hardcode URLs  
❌ Cannot know instance health  
❌ Cannot load balance  
❌ Cannot scale horizontally  

### ✔ Solution → **Service Discovery**

Service Discovery = *address book of microservices*

It tells you:

- Which services are running  
- Their IP + Port  
- Their health status  
- Number of instances  
- Which instance is free  
- Auto-Load balancing  

### Service Discovery Types:

| Type | Description | Example |
|------|-------------|---------|
| **1. Client-Side Discovery** | Client finds service instance from registry | Eureka |
| **2. Server-Side Discovery** | Load balancer chooses instance | AWS ELB |
| **3. DNS-Based Discovery** | Instances registered via DNS | Kubernetes |
| **4. Sidecar Discovery** | Service mesh proxy handles discovery | Istio + Envoy |

Our project uses:

👉 **Netflix Eureka – Client-Side Service Discovery**

---

# 📡 3. Eureka Server

Eureka Server is the **registry center**.

### Responsibilities:
- Store list of available services  
- Update health of services  
- Provide lookup for clients  
- Enable load balancing  
- Handle auto registration / de-registration  

### Eureka Server URL:

http://localhost:8761/


You will see all microservices registered as:

✔ UP  
✔ Healthy  
✔ Auto-refreshed  

---

# ⚙️ 4. Microservices in This Repository

### ✅ 1. Eureka-Server  
Runs at: `8761`  
Responsible for registration & discovery.

---

### ✅ 2. DocterService  
A doctor information service.  
Registers itself to Eureka.

Endpoints:

/doctors
/location


---

### ✅ 3. DoctorPortal  
Acts like an API client / consumer.

It calls DocterService using:

✔ Eureka Client  
✔ RestTemplateBuilder (or Feign)  
✔ Dynamic discovery  

Example internal call:



InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("DOCTERSERVICE", false);
String url = instanceInfo.getHomePageUrl() + "/location";


---

### ✅ 4. PatientService  
A simple microservice that can also communicate with others.

---

### Optional  
### 🟦 5. DiseaseService  
Extra service for experimentation.

---

# 🔄 5. How Services Register in Eureka?

Once a microservice starts:

1. Sends `REGISTER` request to Eureka  
2. Eureka marks service as **STARTING**  
3. After health check → marked **UP**  
4. Every 30 seconds sends a **heartbeat**  
5. Eureka removes if heartbeat fails  

---

# 🔁 6. Inter-Service Communication

There are **3 ways** to communicate:

## 1️⃣ Using RestTemplate (old but simple)

```java
InstanceInfo info = eurekaClient.getNextServerFromEureka("DOCTERSERVICE", false);
String baseUrl = info.getHomePageUrl();

return restTemplate.getForObject(baseUrl + "/location", String.class);

2️⃣ Using LoadBalancerClient (improves balancing)
ServiceInstance instance = loadBalancer.choose("DOCTERSERVICE");
String url = instance.getUri() + "/location";

3️⃣ Using OpenFeign (BEST, recommended)
@FeignClient("DOCTERSERVICE")
public interface DoctorFeign {
    @GetMapping("/location")
    String getLocation();
}

📦 7. How to Run the Entire System
Step 1 — Start Eureka Server
cd Eureka-Server
mvn spring-boot:run

Step 2 — Start DocterService
cd DocterService
mvn spring-boot:run

Step 3 — Start DoctorPortal
cd DoctorPortal
mvn spring-boot:run

Step 4 — Start PatientService
cd PatientService
mvn spring-boot:run

🔍 8. Testing the APIs
DoctorService:
http://localhost:8082/doctors
http://localhost:8082/location

DoctorPortal:
http://localhost:8087/portal-doctors


This endpoint internally calls the DocterService discovered via Eureka.

📁 9. Project Folder Structure
.
├── Eureka-Server/
│   ├── pom.xml
│   ├── src/main/java
│   └── application.properties
│
├── DocterService/
│   ├── pom.xml
│   ├── src/main/java
│   └── application.properties
│
├── DoctorPortal/
│   ├── pom.xml
│   ├── src/main/java
│   └── application.properties
│
├── PatientService/
│   ├── pom.xml
│   ├── src/main/java
│   └── application.properties
│
└── README.md

🌱 10. Future Enhancements

✔ Add API Gateway (Spring Cloud Gateway)
✔ Add Load Balancing (Spring Cloud LoadBalancer)
✔ Implement OpenFeign
✔ Add Config Server
✔ Add Zipkin for Distributed Tracing
✔ Add Docker support
