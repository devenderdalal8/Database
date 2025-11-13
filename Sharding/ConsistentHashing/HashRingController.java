package Sharding.ConsistentHashing;

import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/hash-ring")
public class HashRingController {

 private final HashRingService hashRingService;

public HashRingController(HashRingService hashRingService) {
 this.hashRingService = hashRingService;
 }

@PostMapping("/node")
 public void addNode(@RequestParam String identifier) {
 hashRingService.addNode(identifier);
 }

@DeleteMapping("/node")
 public void removeNode(@RequestParam String identifier) {
 hashRingService.removeNode(identifier);
 }

@GetMapping("/node")
 public Node getNode(@RequestParam String key) {
 return hashRingService.getNode(key);
 }

@GetMapping("/nodes")
 public Collection<Node> getAllNodes() {
 return hashRingService.getAllNodes();
 }

}