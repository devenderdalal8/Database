package Consisting_Hashing;

public class Main {
    public static void main(String[] args) {

        ConsistentHashing<String> ch = new ConsistentHashing<>(3);

        ch.addNode("Server-A");
        ch.addNode("Server-B");
        ch.addNode("Server-C");

        System.out.println("user1 -> " + ch.getNode("user1"));
        System.out.println("user2 -> " + ch.getNode("user2"));
        System.out.println("user3 -> " + ch.getNode("user3"));

        System.out.println("\nRemoving Server-B...\n");

        ch.removeNode("Server-B");

        System.out.println("user1 -> " + ch.getNode("user1"));
        System.out.println("user2 -> " + ch.getNode("user2"));
        System.out.println("user3 -> " + ch.getNode("user3"));
    }
}
