# Database Replication in System Design

Database replication is the process of creating and maintaining duplicate copies of a database on multiple servers. This technique is essential for improving modern systems' **scalability**, **reliability**, and **data availability** by distributing data across servers to ensure accessibility even during failures.

---

## Importance of Database Replication

- **High Availability:** Keeps applications running uninterrupted by maintaining multiple copies of data across servers. If one server fails, others take over seamlessly.
- **Disaster Recovery:** Enables quick data restoration and business continuity by storing data copies at different locations.
- **Load Balancing:** Distributes read queries across multiple servers, reducing the load on a single server and improving overall performance.
- **Fault Tolerance:** Ensures minimal disruption by allowing another server to take over if one fails.
- **Scalability:** Supports growth by distributing write operations and overall load among servers.
- **Data Locality:** Brings data closer to users geographically, reducing latency and enhancing user experience.

---

## How Database Replication Works

1. **Identify the Primary Database (Source):** Choose the main database where all data changes originate.
2. **Set Up Replica Databases (Targets):** Configure one or more secondary databases to receive data from the primary.
3. **Data Changes Captured:** Track inserts, updates, and deletes in the primary database via transaction logs or change data capture mechanisms.
4. **Transmit Changes to Replicas:** Send captured changes over the network, either in real-time or scheduled batches.
5. **Apply Changes on Replicas:** Ensure replicas apply the updates to stay in sync with the primary database.
6. **Monitor and Maintain Synchronization:** Continuously check replicas for lag, conflicts, or delays and address them.
7. **Read or Write Operations:** Direct write operations to the primary and read operations to replicas depending on the replication model (e.g., Master-Slave, Master-Master).

---

## Types of Database Replication

- **Master-Slave Replication:** All writes occur on the master; slaves replicate changes for read scalability.
- **Master-Master Replication (Multi-Master):** Multiple masters accept writes, replicating changes bidirectionally.
- **Snapshot Replication:** Copies the entire database state at a specific moment and replicates it.
- **Transactional Replication:** Continuously replicates individual changes in near real-time.
- **Merge Replication:** Allows changes on both publisher and subscribers, resolving conflicts as needed.

---

## Strategies for Database Replication

- **Full Replication:** Entire database replicated on each server; suitable for read-heavy loads and high availability, but requires significant storage and bandwidth.
- **Partial Replication:** Only parts of the database (specific tables or rows) are replicated to improve resource usage.
- **Selective Replication:** Replication based on predefined criteria or filters for more granular data control.
- **Sharding:** Partitioning data horizontally into shards distributed across multiple databases to improve performance and scalability.
- **Hybrid Replication:** Combines multiple replication strategies tailored for specific parts of the database.

---

## Configurations of Database Replication

- **Synchronous Replication:** Data is replicated in real-time, and transactions commit only after at least one replica confirms receipt. Ensures high consistency but can increase latency.
- **Asynchronous Replication:** Transactions commit immediately on the primary; replicas are updated with some delay. Offers better performance but may have temporary data discrepancies.
- **Semi-synchronous Replication:** A hybrid approach where the primary waits for acknowledgment from at least one replica but others may lag behind.

---

## Challenges with Database Replication

- **Data Consistency:** Ensuring all copies are synchronized, especially with asynchronous modes.
- **Complexity:** Setup and management can be complicated, increasing operational overhead.
- **Cost:** Requires more hardware and network resources.
- **Conflict Resolution:** Handling data conflicts in multi-master replication.
- **Latency:** Synchronous replication may slow down transactions due to waiting on replica acknowledgments.

---

# References
- [Setting Up Read Replication with Spring Boot Using JPA](https://medium.com/@AlexanderObregon/setting-up-read-replication-with-spring-boot-using-jpa-eac7182bf86f)
- [Database Replication in System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/database-replication-in-system-design/)
- [What is Scalability and How to achieve it? - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/what-is-scalability/)
- [Reliability in System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/reliability-in-system-design/)
- [Availability in System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/availability-in-system-design/)
- [High Availability in System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/what-is-high-availability-in-system-design/)
- [Load Balancer System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/what-is-load-balancer-system-design/)
- [Fault Tolerance in System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/fault-tolerance-in-system-design/)
- [Latency in System Design - GeeksforGeeks](https://www.geeksforgeeks.org/system-design/latency-in-system-design/)