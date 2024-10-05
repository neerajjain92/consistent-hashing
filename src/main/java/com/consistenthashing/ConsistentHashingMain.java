package com.consistenthashing;

import com.consistenthashing.hashing.ConsistentHashRing;
import com.consistenthashing.hashing.HashFunction;
import com.consistenthashing.hashing.Murmur3HashFunction;
import com.consistenthashing.node.Node;


public class ConsistentHashingMain {

    public static void main(String[] args) {
        HashFunction hashFunction = new Murmur3HashFunction();
        ConsistentHashRing consistentHashRing = new ConsistentHashRing(hashFunction, 100);

        Node node1 = new Node("192.168.1.1");
        Node node2 = new Node("192.168.1.2");
        Node node3 = new Node("192.168.1.3");

        consistentHashRing.addNode(node1);
        consistentHashRing.addNode(node2);
        consistentHashRing.addNode(node3);

        final String key = "mySampleKey";
        Node responsibleNode = consistentHashRing.getServerForKey(key);
        System.out.println("Key '" + key + "' is mapped to node: " + responsibleNode);
    }
}
