<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=2671E5&height=200&section=header&text=Saga%20Sharded%20Wallet&fontSize=50&fontColor=ffffff&animation=fadeIn&fontAlignY=38&desc=Enterprise-Grade%20Distributed%20Transactions&descAlignY=55&descAlign=50" alt="Header Banner" />
</div>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Architecture-Distributed_Systems-2671E5?style=for-the-badge" alt="Architecture"/>
</p>

<h3 align="center">A Highly Scalable, Event-Driven Digital Wallet Backend</h3>

---

> ### 👔 Executive Summary for Hiring Managers
> **The Problem:** In a standard application, moving money from User A to User B is easy. But at an enterprise scale across multiple distributed servers, what happens if the server crashes *after* User A is charged, but *before* User B receives the money? 
> 
> **My Solution:** I engineered this backend to solve that exact problem. By implementing the **Saga Architecture Pattern** and **Database Sharding**, this system ensures:
> * **Zero Data Loss:** Automated rollbacks guarantee financial integrity (Eventual Consistency).
> * **Massive Scalability:** Sharded databases prevent bottlenecks during peak traffic.
> * **Fault Isolation:** If one microservice goes down, the rest of the system survives.

---

## 📐 Enterprise System Architecture

```mermaid
graph TD
    %% Custom Styles
    classDef gateway fill:#2b2d42,stroke:#8d99ae,stroke-width:2px,color:#fff,rx:5px,ry:5px;
    classDef service fill:#00509d,stroke:#002855,stroke-width:2px,color:#fff,rx:5px,ry:5px;
    classDef orchestrator fill:#d00000,stroke:#9d0208,stroke-width:2px,color:#fff,rx:5px,ry:5px;
    classDef db fill:#023020,stroke:#01120b,stroke-width:2px,color:#fff,rx:5px,ry:5px;
    classDef queue fill:#e0a96d,stroke:#5c4033,stroke-width:2px,color:#000,rx:5px,ry:5px;
    
    Client([📱 Client Application]) --> API[🌐 API Gateway]:::gateway
    API -->|Init Transfer| Saga{⚙️ Saga Orchestrator}:::orchestrator
    
    subgraph Event-Driven Microservices Layer
        Saga -->|1. Emit Debit Event| EventBus[🔀 Async Event Bus]:::queue
        EventBus --> ServiceA[💳 Wallet Service A]:::service
        EventBus --> ServiceB[💳 Wallet Service B]:::service
    end
    
    subgraph Distributed Data Layer
        ServiceA --> DB1[(🗄️ DB Shard 1)]:::db
        ServiceA --> DB2[(🗄️ DB Shard 2)]:::db
        ServiceB --> DB1
        ServiceB --> DB2
    end
    
    Saga -.->|2. On Service Crash: Trigger Rollback| EventBus
