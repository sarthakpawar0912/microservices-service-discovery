# 🏥 Microservices Service Discovery System

A complete microservices ecosystem demonstrating **Eureka Server**, **Eureka Clients**, **Inter-Service Communication**, **Load Balancing**, and **Dynamic Port Discovery**.  
Built using **Spring Boot**, **Spring Cloud Netflix**, and **Java**.

---

# 📘 Table of Contents
1. [Architecture Diagram](#-1-architecture-diagram)  
2. [What is Service Discovery?](#-2-what-is-service-discovery)  
3. [Why Service Discovery is Required?](#-3-why-do-we-need-service-discovery)  
4. [Types of Service Discovery](#-4-types-of-service-discovery)  
5. [Project Services Overview](#-5-microservices-in-this-repository)  
6. [Inter-Service Communication](#-6-inter-service-communication)  
7. [How to Run the Entire System](#-7-how-to-run-the-entire-system)  
8. [Testing the APIs](#-8-testing-the-apis)  
9. [Project Folder Structure](#-9-project-folder-structure)  
10. [Future Enhancements](#-10-future-enhancements)  
11. [Author](#-11-author)

---

# 🌐 1. Architecture Diagram
                    +-----------------------+
                    |     Doctor Portal     |
                    | (Eureka Client + API) |
                    +-----------+-----------+
                                |
                                |  REST Call
                                |  (RestTemplate / Feign)
                                |
        +-----------------------+------------------------+
        |                                                |
        |                                                |
+---------------+                              +----------------+
| PatientService|                              | DocterService  |
| (EurekaClient)|                              | (EurekaClient) |
+-------+-------+                              +-------+--------+
        \                                                /
         \                                              /
          \                                            /
           \                                          /
            \                                        /
             \                                      /
              +------------------------------------+
              |            Eureka Server            |
              |      (Service Registry + Health)    |
              +------------------------------------+

---

# 🔍 2. What is Service Discovery?

Microservices **do not run on fixed ports**.  
Their ports may change every time they restart:

- Today → `DocterService` on **8082**
- Tomorrow → on **8084**
- In production → **multiple dynamic instances**

You **cannot** hardcode URLs like:  
`http://localhost:8082/doctors`

➡️ If the port changes → your entire system breaks.

**Service Discovery solves this problem.**

---

# 🎯 3. Why Do We Need Service Discovery?

### Without Service Discovery:
- ❌ Services cannot find each other
- ❌ Hardcoded URLs break on restart
- ❌ No load balancing
- ❌ Cannot scale horizontally
- ❌ System becomes fragile in distributed environments

### With Service Discovery:
- ✅ Services auto-register themselves
- ✅ Other services auto-discover them dynamically
- ✅ No hardcoded IPs/ports
- ✅ Built-in load balancing
- ✅ Health checks & instance tracking
- ✅ Smooth scaling and restarts

> **Service Discovery = Dynamic address book of all microservices**

---

# 🧭 4. Types of Service Discovery

| Type                    | Description                                      | Example            |
|-------------------------|--------------------------------------------------|--------------------|
| **Client-Side Discovery** | Client asks registry which instance to call     | **Netflix Eureka** |
| **Server-Side Discovery** | External load balancer routes traffic            | AWS ELB, NGINX     |
| **DNS-Based**            | DNS resolves to available service IPs            | Kubernetes         |
| **Service Mesh / Sidecar** | Sidecar proxy handles discovery & routing       | Istio + Envoy      |

### This project uses:  
**Netflix Eureka → Client-Side Service Discovery**

---

# 🩺 5. Microservices in This Repository

| Service           | Port  | Description                                      | Eureka Client |
|-------------------|-------|--------------------------------------------------|---------------|
| **Eureka-Server**     | 8761  | Central service registry & dashboard            | No            |
| **DocterService**     | 8082  | Provides doctor-related APIs                     | Yes           |
| **DoctorPortal**      | 8087  | Frontend/API gateway that calls DocterService    | Yes           |
| **PatientService**    | 8083  | Patient management microservice                  | Yes           |
| **DiseaseService**    | 8085  | Optional service for multi-service testing       | Yes           |

---

# 🔄 6. Inter-Service Communication

There are **3 ways** to call another service via Eureka:

### 1️⃣ Using `RestTemplate` + `EurekaClient` (Basic)

InstanceInfo info = eurekaClient.getNextServerFromEureka("DOCTERSERVICE", false);
String baseUrl = info.getHomePageUrl();
return restTemplate.getForObject(baseUrl + "/location", String.class);

2️⃣ Using LoadBalancerClient (Better Load Balancing)
JavaServiceInstance instance = loadBalancer.choose("DOCTERSERVICE");
String url = instance.getUri() + "/location";
return restTemplate.getForObject(url, String.class);
3️⃣ Using OpenFeign (Recommended - Clean & Declarative)
Java@FeignClient(name = "DOCTERSERVICE")
public interface DoctorFeignClient {
    @GetMapping("/location")
    String getLocation();
}

🛠 7. How to Run the Entire System
Start services in this exact order:
Bash# Step 1: Start Eureka Server (Registry)
cd Eureka-Server
mvn spring-boot:run
➡️ Open Eureka Dashboard: http://localhost:8761
Bash# Step 2: Start DocterService
cd DocterService
mvn spring-boot:run
Bash# Step 3: Start PatientService (optional)
cd PatientService
mvn spring-boot:run
Bash# Step 4: Start DoctorPortal (Consumer)
cd DoctorPortal
mvn spring-boot:run
Wait a few seconds → All services will register with Eureka automatically.

🧪 8. Testing the APIs


EndpointDescriptionGET http://localhost:8082/locationDirect call to DocterServiceGET http://localhost:8087/portal-doctorsDoctorPortal calls DocterService via EurekaGET http://localhost:8761Eureka Dashboard - See all registered services

📁 9. Project Folder Structure
text.
├── Eureka-Server/
├── DocterService/
├── DoctorPortal/
├── PatientService/
├── DiseaseService/        (optional)
└── README.md

🚀 10. Future Enhancements

FeaturePurposeSpring Cloud GatewayAPI Gateway & RoutingSpring Cloud Config ServerCentralized ConfigurationSpring Cloud LoadBalancerAdvanced client-side load balancingOpenFeign + Hystrix/Resilience4jDeclarative clients + Circuit BreakerZipkin + SleuthDistributed TracingDocker + Docker ComposeContainerizationKubernetesOrchestration & Deployment

👨‍💻 11. Author
Sarthak Pawar
Backend Developer | Java | Spring Boot | Microservices Architect
