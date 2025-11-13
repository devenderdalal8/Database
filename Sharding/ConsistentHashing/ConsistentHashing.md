# What is the use of Consistent Hashing?
Consistent hashing is a popular technique used in distributed systems to address the challenge of efficiently distributing keys or data elements across multiple nodes/servers in a network. Consistent hashing's primary objective is to reduce the number of remapping operations necessary when adding or removing nodes from the network, which contributes to the stability and dependability of the system.

* Consistent hashing can be used in to share the burden among nodes and lessen the effects of node failures.
* For example, when a new node is added to the network, only a small number of keys are remapped to the new node, which helps to reduce the overhead associated with the addition.
* Similarly, when a node fails, only a small number of keys are affected, which helps to minimize the impact of the failure on the system as a whole.  
* Consistent hashing is also useful in ensuring data availability and consistency in a distributed system.

# Phases/Working of Consistent Hashing
The following are the phases involved in the process of consistent hashing: 

*Phase 1:* Hash Function Selection: Selecting the hash algorithm to link keys to network nodes is the first stage in consistent hashing. This hash function should be deterministic and produce a different value for each key. The selected hash function will be used to map keys to nodes in a consistent and predictable manner.
*Phase 2:* Node Assignment: Based on the hash function's findings, nodes in the network are given keys in this phase. The nodes are organized in a circle, and the keys are given to the node that is situated closest to the key's hash value in a clockwise direction in the circle.
*Phase 3:* Key Replication: It's critical to make sure that data is accessible in a distributed system even in the case of node failures. Keys can be copied across a number of network nodes to accomplish this. In the event that one node fails, this helps to guarantee that data is always accessible.
*Phase 4:* Node Addition/Removal: It can be required to remap the keys to new nodes in order to maintain system balance when nodes are added to or deleted from the network. By only remapping just a small number of keys to the new node, consistent hashing minimizes the impact of added or deleted nodes.
*Phase 5:* Load balancing: Consistent hashing helps in distributing the load among the network's nodes. To keep the system balanced and effective when a node is overloaded, portions of its keys can be remapped to other nodes.
*Phase 6:* Failure Recovery: If a node fails, the keys that are assigned to it can be remapped to other nodes in the network. This enables data to remain accurate and always available, even in the case of a node failure.
![Example of Hashing](image.png)

# Implementation of Consistent Hashing algorithm
* Step 1: Choose a Hash Function:
 - Select a hash function that produces a uniformly distributed range of hash values. - Common choices include MD5, SHA-1, or SHA-256.
* Step 2: Define the Hash Ring:
- Represent the range of hash values as a ring. This ring should cover the entire   possible range of hash values and be evenly distributed.
* Step 3: Assign Nodes to the Ring:
- Assign each node in the system a position on the hash ring. This is typically done by hashing the node's identifier using the chosen hash function.
* Step 4: Key Mapping:
- When a key needs to be stored or retrieved, hash the key using the chosen hash function to obtain a hash value.
- Find the position on the hash ring where the hash value falls.
- Walk clockwise on the ring to find the first node encountered. This node becomes the owner of the key.
* Step 5: Node Additions:
- When a new node is added, compute its position on the hash ring using the hash function.
- Identify the range of keys that will be owned by the new node. This typically involves finding the predecessor node on the ring.
- Update the ring to include the new node and remap the affected keys to the new node.
* Step 6: Node Removals:
- When a node is removed, identify its position on the hash ring.
- Identify the range of keys that will be affected by the removal. This typically involves finding the successor node on the ring.
- Update the ring to exclude the removed node and remap the affected keys to the successor node.
* Step 7: Load Balancing:
- Periodically check the load on each node by monitoring the number of keys it owns.
- If there is an imbalance, consider redistributing some keys to achieve a more even distribution.

# Advantages of using Consistent Hashing
Below are some of the key advantages of using consistent hashing:

* Load balancing: Even as the volume of data grows and evolves over time, consistent hashing maintains the system's efficiency and responsiveness by distributing the network's workload among its nodes in a balanced way.
* Scalability: Because consistent hashing is so scalable, it can adjust to variations in the number of nodes or volume of data being processed with negligible to no impact on the system's overall performance.
* Minimal Remapping: By minimizing the amount of keys that need to be remapped whenever a node is added or withdrawn, consistent hashing makes sure that the system remains stable and reliable even as the network evolves. 
* Increased Failure Tolerance: Consistent hashing makes data always accessible and current, even in the case of node failures. The stability and dependability of the system as a whole are enhanced by the capacity to replicate keys across several nodes and remap them to different nodes in the event of failure.
* Simplified Operations: The act of adding or removing nodes from the network is made easier by consistent hashing, which makes it simpler to administer and maintain a sizable distributed system.

# Disadvantages of using Consistent Hashing
* Hash Function Complexity: The effectiveness of consistent hashing depends on the use of a suitable hash function. The hash function must produce a unique value for each key and be deterministic in order to be useful. The system's overall effectiveness and efficiency may be affected by how complicated the hash function is.
* Performance Cost: The computing resources needed to map keys to nodes, replicate keys, and remap keys in the event of node additions or removals can result in some performance overhead when using consistent hashing.
* Lack of Flexibility: In some circumstances, the system's ability to adapt to changing requirements or shifting network conditions may be constrained by the rigid limits of consistent hashing. 
* High Resource Use: As nodes are added to or deleted from the network, consistent hashing may occasionally result in high resource utilization. This can have an effect on the system's overall performance and efficacy.
* The complexity of Management: Managing and maintaining a system that uses consistent hashing can be difficult and demanding, and it often calls for particular expertise and abilities.