# 🏥 Microservices Service Discovery System  
A complete microservices ecosystem demonstrating **Eureka Server**, **Eureka Clients**, **Inter-Service Communication**, **Load Balancing**, and **Dynamic Port Discovery**.  
Built using **Spring Boot**, **Spring Cloud Netflix**, and **Java**.

---

# 📘 Table of Contents
1. Architecture Diagram  
2. What is Service Discovery?  
3. Why Service Discovery is Required?  
4. Types of Service Discovery  
5. Project Services Overview  
6. Inter-Service Communication  
7. How to Run the Entire System  
8. Project Folder Structure  
9. Future Enhancements  
10. Author  

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

- Today DocterService → **8082**  
- Tomorrow → **8084**  
- In production → **multiple dynamic instances**  

You cannot hardcode URLs like:


http://localhost:8082/doctors

yaml
Copy code

If port changes → your system breaks.

👉 **Service Discovery solves this problem.**

---

# 🎯 3. Why Do We Need Service Discovery?

Without Service Discovery:

❌ Services cannot find each other  
❌ You can't load balance  
❌ You can’t scale horizontally  
❌ Hardcoded URLs break  
❌ In distributed systems, everything becomes unstable  

With Service Discovery:

✔ Services auto-register  
✔ Other services auto-discover them  
✔ No hardcoded URLs  
✔ Load balancing becomes automatic  
✔ Restarts don’t affect communication  
✔ Real-time health checking  

Service Discovery = **Dynamic address book of all microservices.**

---

# 🧭 4. Types of Service Discovery

| Type | Description | Example |
|------|-------------|---------|
| **1. Client-Side Discovery** | Client directly asks registry which instance to call | Eureka |
| **2. Server-Side Discovery** | Load balancer picks instance for you | AWS ELB |
| **3. DNS-Based** | DNS resolves service IPs dynamically | Kubernetes |
| **4. Service Mesh / Sidecar** | Proxy handles discovery & traffic | Istio + Envoy |

### 👉 Our project uses:  
**Netflix Eureka = Client-Side Service Discovery**

---

# 🧩 5. Microservices in This Repository

### ✔ 1. Eureka-Server  
- Port: `8761`  
- Registry for all clients  
- Tracks health, instances, and availability  

### ✔ 2. DocterService  
- Doctor service  
- Auto-registers with Eureka  
- Endpoints:  
  - `/doctors`  
  - `/location`  

### ✔ 3. DoctorPortal  
- Acts as API client  
- Calls DocterService using Eureka Discovery  
- Shows dynamic communication  

### ✔ 4. PatientService  
- Basic patient microservice  
- Also registers with Eureka  

### ✔ 5. DiseaseService *(optional)*  
- Extra service for testing multi-service architecture  

---

# 🔄 6. Inter-Service Communication

There are **3 ways**:

---

## 1️⃣ Using RestTemplate (simple & working)

```java
InstanceInfo info = eurekaClient.getNextServerFromEureka("DOCTERSERVICE", false);
String baseUrl = info.getHomePageUrl();

return restTemplate.getForObject(baseUrl + "/location", String.class); ```

2️⃣ Using LoadBalancerClient (better load balancing)
java
Copy code
ServiceInstance instance = loadBalancer.choose("DOCTERSERVICE");
String url = instance.getUri() + "/location";
3️⃣ Using OpenFeign (modern, clean, recommended)
java
Copy code
@FeignClient("DOCTERSERVICE")
public interface DoctorFeign {
    @GetMapping("/location")
    String getLocation();
}
🧪 7. How to Run the Entire System
Follow this order:

Step 1 — Start Eureka Server
arduino
Copy code
cd Eureka-Server
mvn spring-boot:run
Open dashboard:

👉 http://localhost:8761/

Step 2 — Start DocterService
arduino
Copy code
cd DocterService
mvn spring-boot:run
Step 3 — Start DoctorPortal
arduino
Copy code
cd DoctorPortal
mvn spring-boot:run
Step 4 — Start PatientService
arduino
Copy code
cd PatientService
mvn spring-boot:run
🔍 8. Testing the APIs
DocterService:
bash
Copy code
http://localhost:8082/location
DoctorPortal (calls DocterService internally):
bash
Copy code
http://localhost:8087/portal-doctors
📁 9. Project Folder Structure
arduino
Copy code
.
├── Eureka-Server/
├── DocterService/
├── DoctorPortal/
├── PatientService/
├── DiseaseService/
└── README.md
🚀 10. Future Enhancements
Enhancement	Purpose
API Gateway (Spring Cloud Gateway)	Central routing
Spring Cloud Config Server	Externalized config
Load Balancing	Using Spring Cloud LoadBalancer
OpenFeign	Clean inter-service calling
Zipkin + Sleuth	Distributed tracing
Resilience4J	Circuit breakers, retries
Dockerization	Containerize all services
Kubernetes	Production-grade orchestration

👨‍💻 11. Author
Sarthak Pawar
Backend Developer | Java | Spring Boot | Microservices

Instagram • GitHub • LinkedIn
