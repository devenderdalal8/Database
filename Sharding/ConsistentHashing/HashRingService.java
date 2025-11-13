package Sharding.ConsistentHashing;

import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class HashRingService {

 private final ConsistentHashing consistentHashRing;

public HashRingService() {
 this.consistentHashRing = new ConsistentHashRing(3); // numberOfReplicas
 }

public void addNode(String identifier) {
 consistentHashRing.addNode(new Node(identifier));
 }

public void removeNode(String identifier) {
 consistentHashRing.removeNode(new Node(identifier));
 }

public Node getNode(String key) {
 return consistentHashRing.getNode(key);
 }

public Collection<Node> getAllNodes() {
 return consistentHashRing.getAllNodes();
 }

}
