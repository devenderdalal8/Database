package Sharding.ConsistentHashing;

import java.util.SortedMap;

public class ConsistentHashing {
 private final SortedMap<Integer, Node> ring = new TreeMap<>();
 private final int numberOfReplicas;

public ConsistentHashRing(int numberOfReplicas) {
 this.numberOfReplicas = numberOfReplicas;
 }

private int hash(String key) {
 return key.hashCode();
 }

public void addNode(Node node) {
 for (int i = 0; i < numberOfReplicas; i++) {
 ring.put(hash(node.getIdentifier() + i), node);
   }
 }

public void removeNode(Node node) {
 for (int i = 0; i < numberOfReplicas; i++) {
 ring.remove(hash(node.getIdentifier() + i));
   }
 }

public Node getNode(String key) {
 if (ring.isEmpty()) {
 return null;
   }
 int hash = hash(key);
 if (!ring.containsKey(hash)) {
 SortedMap<Integer, Node> tailMap = ring.tailMap(hash);
 hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
   }
 return ring.get(hash);
 }

public Collection<Node> getAllNodes() {
 return new HashSet<>(ring.values());
   }
 }