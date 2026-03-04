package Consisting_Hashing;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.CRC32;
public class ConsistentHashing<T> {

    SortedMap<Long, T> ring = new TreeMap<>();

    private final int numberOfReplicas; // Virtual nodes per physical node

    ConsistentHashing(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    void addNode(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            long hash = hash(node.toString() + "-" + i);
            ring.put(hash, node);
        }
    }

    void removeNode(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            long hash = hash(node.toString() + "-" + i);
            ring.remove(hash);
        }
    }

    public T getNode(String key) {
        if (ring.isEmpty()) {
            return null;
        }
        long hash = hash(key);
        if (!ring.containsKey(hash)) {
            SortedMap<Long, T> tailMap = ring.tailMap(hash);
            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }
        return ring.get(hash);
    }

    private long hash(String key) {
        CRC32 crc = new CRC32();
        crc.update(key.getBytes());
        return crc.getValue();
    }

    // ------------------------------
    // Print Ring (Debug)
    // ------------------------------
    public void printRing() {
        for (Map.Entry<Long, T> entry : ring.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
